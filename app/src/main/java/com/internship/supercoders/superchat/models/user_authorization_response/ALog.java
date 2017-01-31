package com.internship.supercoders.superchat.models.user_authorization_response;

import com.google.gson.annotations.SerializedName;


public class ALog {
    @SerializedName("application_id")
    String appId;
    @SerializedName("auth_key")
    String auth_key;
    @SerializedName("timestamp")
    String timestamp;
    @SerializedName("nonce")
    String nonce;
    @SerializedName("signature")
    String signature;
    @SerializedName("user")
    VerificationData VerificationData;

    public ALog(String appId, String auth_key, String timestamp, String nonce,String signature, VerificationData VerificationData) {
        this.appId = appId;
        this.auth_key = auth_key;
        this.timestamp = timestamp;
        this.nonce = nonce;
        this.VerificationData = VerificationData;
        this.signature = signature;
    }
}
