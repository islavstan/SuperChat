package com.internship.supercoders.superchat.models.user_authorization_response;

import com.google.gson.annotations.SerializedName;


public class AuthUser {
    @SerializedName("user")
    VerificationData verificationData;

    public AuthUser(VerificationData verificationData) {
        this.verificationData = verificationData;
    }

    public VerificationData getVerificationData() {
        return verificationData;


    }
}
