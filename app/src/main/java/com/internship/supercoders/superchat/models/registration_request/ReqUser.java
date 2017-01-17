package com.internship.supercoders.superchat.models.registration_request;

import com.google.gson.annotations.SerializedName;



public class ReqUser {
    @SerializedName("user")
    ReqUserData reqUserData;

    public ReqUser(ReqUserData reqUserData) {
        this.reqUserData = reqUserData;
    }
}
