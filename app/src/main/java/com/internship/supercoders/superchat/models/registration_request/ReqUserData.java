package com.internship.supercoders.superchat.models.registration_request;

import com.google.gson.annotations.SerializedName;


public class ReqUserData {
    /* @SerializedName("login")
     String login;*/
    @SerializedName("password")
    String password;
    @SerializedName("email")
    String email;
    @SerializedName("full_name")
    String fullname;

    @SerializedName("phone")
    String phone;
    @SerializedName("website")
    String website;

    public ReqUserData(String password, String email, String fullname, String phone, String website) {
        this.password = password;
        this.email = email;
        this.fullname = fullname;
        this.phone = phone;
        this.website = website;
    }
}
