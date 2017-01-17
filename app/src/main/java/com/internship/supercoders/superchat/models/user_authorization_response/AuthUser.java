package com.internship.supercoders.superchat.models.user_authorization_response;

import com.google.gson.annotations.SerializedName;


public class AuthUser {
    @SerializedName("user")
    LogAndPas logAndPas;

    public AuthUser(LogAndPas logAndPas) {
        this.logAndPas = logAndPas;
    }

    public LogAndPas getLogAndPas() {
        return logAndPas;


    }
}
