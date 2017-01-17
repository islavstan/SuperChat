package com.internship.supercoders.superchat.points;

import com.internship.supercoders.superchat.models.authorization_response.Session;
import com.internship.supercoders.superchat.models.registration_request.ReqUser;
import com.internship.supercoders.superchat.models.user_authorization_response.ALog;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;



public interface UserAuthorizatoinPoint {
    @Headers({
            "Content-Type: application/json",
            "QuickBlox-REST-API-Version: 0.1.0"
    })
    @POST("/session.json")
    Call<Session> userAuthorizatoin(@Body ALog user);

}