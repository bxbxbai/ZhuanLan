package io.bxbxbai.zhuanlan.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by baia on 15/3/13.
 *
 * @author bxbxbai
 */
public class Post {

    @SerializedName("rating")
    private String rating;

    @SerializedName("sourceUrl")
    private String sourceUrl;

    @SerializedName("publishedTime")
    private String publishedTime;

    @SerializedName("links")
    private Comment comment;

    @SerializedName("author")
    private Author author;

    @SerializedName("column")
    private Column column;

    @SerializedName("title")
    private String title;

    @SerializedName("titleImage")
    private String titleImage;

    @SerializedName("summary")
    private String summary;

    @SerializedName("content")
    private String content;

    @SerializedName("url")
    private String url;

    @SerializedName("state")
    private String state;

    @SerializedName("href")
    private String href;

    @SerializedName("commentsCount")
    private int commentsCount;

    @SerializedName("likesCount")
    private int likesCount;

    public String getRating() {
        return rating;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String getPublishedTime() {
        return publishedTime;
    }

    public Comment getComment() {
        return comment;
    }

    public Author getAuthor() {
        return author;
    }

    public Column getColumn() {
        return column;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public String getSummary() {
        return summary;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    public String getState() {
        return state;
    }

    public String getHref() {
        return href;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public class Comment {
        @SerializedName("comments")
        String comments;
    }

    public class Column {
        @SerializedName("slug")
        String slug;

        @SerializedName("name")
        String name;
    }

}
