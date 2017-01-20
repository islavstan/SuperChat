package com.internship.supercoders.superchat.models.blob;

import com.google.gson.annotations.SerializedName;

/**
 * Created by islav on 17.01.2017.
 */

public class BlobObjectAccess {
    @SerializedName("params")
    String params;

    public String getParams() {
        return params;
    }
}
