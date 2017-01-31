package com.internship.supercoders.superchat.models.user_authorization_response;

import com.google.gson.annotations.SerializedName;


public class VerificationData {
    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;

    public VerificationData(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
