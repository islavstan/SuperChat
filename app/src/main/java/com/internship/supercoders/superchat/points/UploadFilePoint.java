package com.internship.supercoders.superchat.points;

import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.util.Objects;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface UploadFilePoint {
    @Multipart

    @POST("/")
    Call<Objects> uploadFile(@Part("Content-Type")RequestBody content, @Part("Expires")RequestBody exp, @Part("acl")RequestBody acl,
                             @Part("key") RequestBody key, @Part("policy")RequestBody policy, @Part("success_action_status")RequestBody sas,
                             @Part("x-amz-algorithm")RequestBody xamz, @Part("x-amz-credential")RequestBody xamzcred,
                             @Part("x-amz-date")RequestBody xamzdate, @Part("x-amz-signature")RequestBody xamzsign,
                             @Part("file") /*MultipartBody.Part*/ RequestBody file);



   /* Call<Objects> uploadFile(@Part("Content-Type")String content, @Field("Expires")String exp, @Field("acl")String acl,
                             @Field("key") String key, @Field("policy")String policy, @Field("success_action_status")String sas,
                             @Field("x-amz-algorithm")String xamz, @Field("x-amz-credential")String xamzcred,
                             @Field("x-amz-date")String xamzdate, @Field("x-amz-signature")String xamzsign,
                             @Field("file")File f);*/

}


