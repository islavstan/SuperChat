package com.internship.supercoders.superchat.models.user_info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Max on 17.02.2017.
 */
public class UserDataFullProfile {
    @SerializedName("id")
    int id;
    @SerializedName("full_name")
    String name;
    @SerializedName("email")
    String email;
    @SerializedName("login")
    String login;
    @SerializedName("phone")
    String phone;
    @SerializedName("website")
    String website;
    @SerializedName("created_at")
    String createdTime;
    @SerializedName("updated_at")
    String updatedTime;
    @SerializedName("last_request_at")
    String lastRequesrTime;
    @SerializedName("external_user_id")
    int externalUserId;
    @SerializedName("facebook_id")
    String facebookId;
    @SerializedName("twitter_id")
    String twitterId;
    @SerializedName("twitter_digits_id")
    int twitterDigitId;
    @SerializedName("blob_id")
    String blobId;
    @SerializedName("user_tags")
    String tags;
    @Expose
    private byte[] avatarObj;
    String photo_path;

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    public UserDataFullProfile(int id, String name, String email, String phone, String website, String blobId, String photo_path) {
        this.id = id;
        this.name = name;
        this.email = email;

        this.phone = phone;
        this.website = website;
        this.blobId = blobId;
        this.photo_path = photo_path;
    }

    public int getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getBlobId() {
        return blobId;
    }

    public byte[] getAvatarObj() {
        return avatarObj;
    }

    public void setAvatarObj(byte[] avatarObj) {
        this.avatarObj = avatarObj;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public String getLastRequesrTime() {
        return lastRequesrTime;
    }

    public int getExternalUserId() {
        return externalUserId;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public String getTwitterId() {
        return twitterId;
    }

    public int getTwitterDigitId() {
        return twitterDigitId;
    }

    public String getTags() {
        return tags;
    }
}
