package com.internship.supercoders.superchat.api;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;



public class RequestBuilder {
    //Upload request body
    public static MultipartBody uploadRequestBody( File file, String contentType, String expires, String acl, String key, String policy, String success_action_status, String x_amz_algorithm, String x_amz_credential, String x_amz_date, String x_amz_signature) {

        MediaType MEDIA_TYPE = MediaType.parse("image/" + "jpg"); // e.g. "image/png"
        return new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("Content-Type", contentType)
                .addFormDataPart("Expires", expires)
                .addFormDataPart("acl", acl) //e.g. title.png --> imageFormat = png
                .addFormDataPart("key", key)
                .addFormDataPart("policy", policy)
                .addFormDataPart("success_action_status", success_action_status)
                .addFormDataPart("x-amz-algorithm", x_amz_algorithm)
                .addFormDataPart("x-amz-credential", x_amz_credential)
                .addFormDataPart("x-amz-date", x_amz_date)
                .addFormDataPart("x-amz-signature", x_amz_signature)


                .addFormDataPart("file", "...", RequestBody.create(MEDIA_TYPE, file))

                .build();
    }
}