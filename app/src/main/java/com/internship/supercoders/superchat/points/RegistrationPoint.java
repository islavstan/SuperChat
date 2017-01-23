package com.internship.supercoders.superchat.points;

import com.internship.supercoders.superchat.models.registration_request.ReqUser;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface RegistrationPoint {

    @POST("/users.json")
    Call <Object>registration(@Header("Content-Type")String cont, @Header("QuickBlox-REST-API-Version")String version, @Header("QB-Token")String token, @Body ReqUser user);
}


