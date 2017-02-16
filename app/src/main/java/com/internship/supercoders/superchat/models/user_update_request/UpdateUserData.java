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
}
