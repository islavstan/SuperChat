package com.internship.supercoders.superchat.points;




import com.internship.supercoders.superchat.models.authorization_response.Session;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthorizationPoint {

    @Headers({
            "Content-Type: application/json",
            "QuickBlox-REST-API-Version: 0.1.0"
    })
    @POST("/session.json")
    Call<Session> getSession(@Body Map<String, String> map);

}