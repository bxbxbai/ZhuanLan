package io.bxbxbai.zhuanlan.task;

import android.text.TextUtils;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import io.bxbxbai.zhuanlan.model.DailyNews;
import io.bxbxbai.zhuanlan.support.Constants;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AccelerateGetNewsTask extends BaseGetNewsTask {
    private String serverCode;

    public AccelerateGetNewsTask(String serverCode, String date, UpdateUIListener callback) {
        super(date, callback);
        this.serverCode = serverCode;
    }

    @Override
    protected List<DailyNews> doInBackground(Void... params) {
        List<DailyNews> resultNewsList = new ArrayList<DailyNews>();

        Type listType = new TypeToken<List<DailyNews>>() {

        }.getType();

        String baseUrl, jsonFromWeb;
        if (serverCode.equals(Constants.ServerCode.SAE)) {
            baseUrl = Constants.Url.ZHIHU_DAILY_PURIFY_SAE_BEFORE;
        } else {
            baseUrl = Constants.Url.ZHIHU_DAILY_PURIFY_HEROKU_BEFORE;
        }

        try {
            jsonFromWeb = downloadStringFromUrl(baseUrl + date);
        } catch (IOException e) {
            isRefreshSuccess = false;
            return null;
        }

        String newsListJSON = decodeHtml(jsonFromWeb);

        if (!TextUtils.isEmpty(newsListJSON)) {
            try {
                resultNewsList = new GsonBuilder().create().fromJson(newsListJSON, listType);
            } catch (JsonSyntaxException ignored) {

            }
        } else {
            isRefreshSuccess = false;
        }

        isContentSame = checkIsContentSame(resultNewsList);
        return resultNewsList;
    }
}
