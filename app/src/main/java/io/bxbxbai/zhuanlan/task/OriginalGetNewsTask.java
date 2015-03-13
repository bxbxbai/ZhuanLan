package io.bxbxbai.zhuanlan.task;

import io.bxbxbai.zhuanlan.model.DailyNews;
import io.bxbxbai.zhuanlan.support.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OriginalGetNewsTask extends BaseGetNewsTask {

    public OriginalGetNewsTask(String date, UpdateUIListener callback) {
        super(date, callback);
    }

    @Override
    protected List<DailyNews> doInBackground(Void... params) {
        List<DailyNews> resultNewsList = new ArrayList<DailyNews>();

        try {
            JSONObject contents = new JSONObject(downloadStringFromUrl(Constants.Url.ZHIHU_DAILY_BEFORE + date));

            JSONArray newsArray = contents.getJSONArray("stories");
            for (int i = 0; i < newsArray.length(); i++) {
                JSONObject singleNews = newsArray.getJSONObject(i);

                DailyNews dailyNews = new DailyNews();
                dailyNews.setThumbnailUrl(singleNews.has("images")
                        ? (String) singleNews.getJSONArray("images").get(0)
                        : null);
                dailyNews.setDailyTitle(singleNews.getString("title"));
                String newsInfoJson =
                        downloadStringFromUrl(Constants.Url.ZHIHU_DAILY_OFFLINE_NEWS + singleNews.getString("id"));
                JSONObject newsDetail = new JSONObject(newsInfoJson);
                if (newsDetail.has("body")) {
                    dailyNews.setHtmlBody(newsDetail.getString("body"));
                    Document doc = Jsoup.parse(newsDetail.getString("body"));
                    if (updateDailyNews(doc, singleNews.getString("title"), dailyNews)) {
                        resultNewsList.add(dailyNews);
                    }
                }
            }
        } catch (JSONException e) {
            isRefreshSuccess = false;
        } catch (IOException e) {
            isRefreshSuccess = false;
        }

        isContentSame = checkIsContentSame(resultNewsList);
        return resultNewsList;
    }

    private boolean updateDailyNews(Document doc, String dailyTitle, DailyNews dailyNews) throws JSONException {
        Elements viewMoreElements = doc.getElementsByClass("view-more");

        if (viewMoreElements.size() > 1) {
            dailyNews.setMulti(true);
            Elements questionTitleElements = doc.getElementsByClass("question-title");

            for (int j = 0; j < viewMoreElements.size(); j++) {
                if (questionTitleElements.get(j).text().length() == 0) {
                    dailyNews.addQuestionTitle(dailyTitle);
                } else {
                    dailyNews.addQuestionTitle(questionTitleElements.get(j).text());
                }

                Elements viewQuestionElement = viewMoreElements.get(j).
                        select("a");

                // Unless the url is a link to zhihu, do not add it to the result NewsList
                if (viewQuestionElement.text().equals("查看知乎讨论")) {
                    dailyNews.addQuestionUrl(viewQuestionElement.attr("href"));
                } else {
                    return false;
                }
            }
        } else if (viewMoreElements.size() == 1) {
            dailyNews.setMulti(false);

            Elements viewQuestionElement = viewMoreElements.select("a");
            if (viewQuestionElement.text().equals("查看知乎讨论")) {
                dailyNews.setQuestionUrl(viewQuestionElement.attr("href"));
            } else {
                return false;
            }

            // Question title is the same with daily title
            if (doc.getElementsByClass("question-title").text().length() == 0) {
                dailyNews.setQuestionTitle(dailyTitle);
            } else {
                dailyNews.setQuestionTitle(doc.getElementsByClass("question-title").text());
            }
        } else {
            return false;
        }

        return true;
    }
}
