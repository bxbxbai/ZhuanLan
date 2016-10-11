package io.bxbxbai.zhuanlan.bean;

import com.google.gson.annotations.SerializedName;

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

    public String getAvatarId() {
        Avatar avatar = author.getAvatar();
        return avatar == null ? this.avatar.getId() : avatar.getId();
    }

    public String getAvatarTemplate() {
        Avatar avatar = author.getAvatar();
        return avatar == null ? this.avatar.getTemplate() : avatar.getTemplate();
    }

    public String getHref() {
        return href;
    }

    public String getSlug() {
        return slug;
    }

    public String getZhuanlanName() {
        return name;
    }

    public String getAuthorName() {
        return author.getName();
    }

    public String getUrl() {
        return url;
    }

    public int getPostCount() {
        return postCount;
    }

    public UserEntity toUserEntity() {
        UserEntity entity = new UserEntity();
        entity.setFollowerCount(getFollowerCount());
        entity.setAuthorName(getAuthorName());
        entity.setAvatarId(getAvatarId());
        entity.setAvatarTemplate(getAvatarTemplate());
        entity.setHref(getHref());
        entity.setZhuanlanName(getZhuanlanName());
        entity.setDescription(getDescription());
        entity.setSlug(getSlug());
        entity.setPostCount(getPostCount());
        entity.setUrl(getUrl());
        return entity;
    }

}
