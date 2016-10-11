package io.bxbxbai.zhuanlan.bean;

import android.os.Parcel;
import android.os.Parcelable;
import orm.db.annotation.Column;
import orm.db.annotation.PrimaryKey;
import orm.db.annotation.Table;
import orm.db.annotation.Unique;
import orm.db.enums.AssignType;

/**
 * Created by xuebin on 15/9/21.
 */
@Table("_user")
public class UserEntity implements Parcelable {

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    @Column("_id")
    public int id;

    @Column("followersCount")
    private int followerCount;

    @Column("description")
    private String description;

    @Column("avatarId")
    private String avatarId;

    @Column("avatarTemplate")
    private String avatarTemplate;

    @Column("authorName")
    private String authorName;

    @Unique
    @Column("href")
    private String href;

    @Unique
    @Column("slug")
    private String slug;

    @Column("name")
    private String name;

    @Column("url")
    private String url;

    @Column("postsCount")
    private int postCount;


    public int getFollowerCount() {
        return followerCount;
    }

    public String getDescription() {
        return description;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public String getAvatarTemplate() {
        return avatarTemplate;
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
        return authorName;
    }

    public String getUrl() {
        return url;
    }

    public int getPostCount() {
        return postCount;
    }

    //setter

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    public void setAvatarTemplate(String avatarTemplate) {
        this.avatarTemplate = avatarTemplate;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setZhuanlanName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.followerCount);
        dest.writeString(this.description);
        dest.writeString(this.avatarId);
        dest.writeString(this.avatarTemplate);
        dest.writeString(this.authorName);
        dest.writeString(this.href);
        dest.writeString(this.slug);
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeInt(this.postCount);
    }

    public UserEntity() {
    }

    protected UserEntity(Parcel in) {
        this.id = in.readInt();
        this.followerCount = in.readInt();
        this.description = in.readString();
        this.avatarId = in.readString();
        this.avatarTemplate = in.readString();
        this.authorName = in.readString();
        this.href = in.readString();
        this.slug = in.readString();
        this.name = in.readString();
        this.url = in.readString();
        this.postCount = in.readInt();
    }

    public static final Parcelable.Creator<UserEntity> CREATOR = new Parcelable.Creator<UserEntity>() {
        @Override
        public UserEntity createFromParcel(Parcel source) {
            return new UserEntity(source);
        }

        @Override
        public UserEntity[] newArray(int size) {
            return new UserEntity[size];
        }
    };
}
