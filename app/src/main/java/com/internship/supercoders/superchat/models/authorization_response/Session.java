package com.internship.supercoders.superchat.models.authorization_response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by islav on 13.01.2017.
 */

public class Session {
    @SerializedName("session")
    Data data;

    public Data getData() {
        return data;
    }
}
