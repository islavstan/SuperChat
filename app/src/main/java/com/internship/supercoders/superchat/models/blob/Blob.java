package com.internship.supercoders.superchat.models.blob;


import com.google.gson.annotations.SerializedName;

public class Blob {
    @SerializedName("blob")
    BlobData blobData;

    public Blob(BlobData blobData) {
        this.blobData = blobData;
    }

    public BlobData getBlobData() {
        return blobData;
    }
}
