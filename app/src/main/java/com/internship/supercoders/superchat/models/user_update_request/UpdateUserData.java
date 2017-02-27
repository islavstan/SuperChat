package com.internship.supercoders.superchat.models.user_update_request;


import com.google.gson.annotations.SerializedName;

public class UpdateUserData {

    @SerializedName("id")
    String id;
    @SerializedName("login")
    String login;
    @SerializedName("blob_id")
    String blob_id;
    @SerializedName("email")
    String email;
    @SerializedName("full_name")
    String full_name;
    @SerializedName("phone")
    String phone;
    @SerializedName("website")
    String website;
    @SerializedName("facebook_id")
    String facebook_id;
    @SerializedName("external_user_id")
    int external_user_id;

    public int getExternal_user_id() {
        return external_user_id;
    }

    public UpdateUserData(String blob_id) {
        this.blob_id = blob_id;
    }

    public String getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getBlob_id() {
        return blob_id;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getWebsite() {
        return website;
    }

    public String getFacebook_id() {
        return facebook_id;
    }
}
