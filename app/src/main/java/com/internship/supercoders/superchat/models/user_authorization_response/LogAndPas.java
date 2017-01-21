package com.internship.supercoders.superchat.models.user_authorization_response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by islav on 14.01.2017.
 */

public class LogAndPas {
    @SerializedName("email")
    public String email;
    @SerializedName("password")
    public String password;

    public LogAndPas(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
