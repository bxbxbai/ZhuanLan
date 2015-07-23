package io.bxbxbai.zhuanlan.bean;

import android.database.Cursor;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *
 * @author bxbxbai
 */
public class User {

    @SerializedName("followersCount")
    private int followerCount;

    @SerializedName("description")
    private String description;

    @SerializedName("creator")
    private Author author;

    @SerializedName("topics")
    private List<Topic> topics;

    @SerializedName("href")
    private String href;

    @SerializedName("slug")
    private String slug;

    @SerializedName("name")
    private String name;

    @SerializedName("url")
    private String url;

    @SerializedName("avatar")
    private Avatar avatar;

    @SerializedName("following")
    private boolean following;

    @SerializedName("postsCount")
    private int postCount;

    public User() {
        author = new Author();
        avatar = new Avatar();


    }

    public int getFollowerCount() {
        return followerCount;
    }

    public String getDescription() {
        return description;
    }

    public Author getAuthor() {
        return author;
    }

    public String getHref() {
        return href;
    }

    public String getSlug() {
        return slug;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public boolean isFollowing() {
        return following;
    }

    public int getPostCount() {
        return postCount;
    }

    public static class Topic {
        @SerializedName("url")
        private String url;

        @SerializedName("id")
        private String id;

        @SerializedName("name")
        private String name;

        public String getUrl() {
            return url;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }


    public static User fromCursor(Cursor cursor) {
        User user = new User();
        Avatar avatar = new Avatar();

        return user;
    }
}
