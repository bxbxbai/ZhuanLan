package io.bxbxbai.zhuanlan.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by baia on 14-6-2.
 */
public class Author {

    public static final String BIO = "bio";

    public static final String HASH = "hash";

    public static final String DESCRIPTION = "description";

    public static final String PROFILE_URL = "profileUrl";

    public static final String AVATAR = "avatar";

    public static final String SLUG = "slug";

    public static final String NAME = "name";

    @SerializedName(BIO)
    private String bio;

    @SerializedName(HASH)
    private String hash;

    @SerializedName(DESCRIPTION)
    private String description;

    @SerializedName(PROFILE_URL)
    private String profileUrl;

    @SerializedName(AVATAR)
    private Avatar avatar;

    @SerializedName(SLUG)
    private String slug;

    @SerializedName(NAME)
    private String name;


    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
