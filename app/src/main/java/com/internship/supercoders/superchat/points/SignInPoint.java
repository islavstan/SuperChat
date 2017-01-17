package com.internship.supercoders.superchat.points;

import com.internship.supercoders.superchat.models.blob.Blob;
import com.internship.supercoders.superchat.models.user_authorization_response.LogAndPas;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;



public interface SignInPoint {
    @POST("/login.json")
    Call<Objects> signIn(@Header("Content-Type")String cont, @Header("QuickBlox-REST-API-Version")String version, @Header("QB-Token")String token, @Body LogAndPas logAndPas);

}
