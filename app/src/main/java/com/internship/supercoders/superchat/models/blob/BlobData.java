package com.internship.supercoders.superchat.models.blob;

import com.google.gson.annotations.SerializedName;



public class BlobData {
    @SerializedName("content_type")
    String content_type;
    @SerializedName("name")
    String name;
    @SerializedName("blob_object_access")
    BlobObjectAccess blobObjectAccess;

    @SerializedName("size")
    int size;

    @SerializedName("id")
    String id;

    public String getId() {
        return id;
    }

    public BlobData(String content_type, String name) {
        this.content_type = content_type;
        this.name = name;
    }

    public BlobObjectAccess getBlobObjectAccess() {
        return blobObjectAccess;
    }

    public BlobData(int size) {
        this.size = size;
    }
}
