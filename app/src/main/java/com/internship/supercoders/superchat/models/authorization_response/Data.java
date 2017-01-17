package com.internship.supercoders.superchat.models.authorization_response;


import com.google.gson.annotations.SerializedName;

public class Data {
  @SerializedName("token")
    String token;

    public String getToken() {
        return token;
    }
}
