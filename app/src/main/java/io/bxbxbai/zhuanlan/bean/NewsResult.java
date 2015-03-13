package io.bxbxbai.zhuanlan.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by baia on 14-9-16.
 */
public class NewsResult {

    @SerializedName("date")
    private String mDate;


    private List<News> newsList;


    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    private class News{
        @SerializedName("title")
        String title;

        @SerializedName("share_url")
        String shareUrl;

        @SerializedName("ga_prefix")
        String gaPrefix;

        @SerializedName("type")
        int type;

        @SerializedName("id")
        int id;

        @SerializedName("images")
        String imageUrls;
    }

}
