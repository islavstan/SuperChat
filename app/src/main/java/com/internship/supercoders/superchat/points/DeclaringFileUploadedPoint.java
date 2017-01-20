package com.internship.supercoders.superchat.points;

import com.internship.supercoders.superchat.models.blob.Blob;
import com.internship.supercoders.superchat.models.user_authorization_response.LogAndPas;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface DeclaringFileUploadedPoint {

    @PUT("/blobs/{id}/complete.json")
    Call<Objects> declaringUpload(@Path("id")String id,@Header("Content-Type")String cont, @Header("QuickBlox-REST-API-Version")String version, @Header("QB-Token")String token, @Body Blob blob);

}



