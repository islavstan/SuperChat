package com.internship.supercoders.superchat.points;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ResetPasswordPoint {

    @GET("users/password/reset.json?")
    Call<Object>resetPassword(@Header("Content-Type")String cont, @Header("QB-Token")String token,@Query("email")String email);

}
