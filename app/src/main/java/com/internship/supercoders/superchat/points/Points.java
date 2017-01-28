package com.internship.supercoders.superchat.points;

import com.internship.supercoders.superchat.models.authorization_response.Session;
import com.internship.supercoders.superchat.models.blob.Blob;
import com.internship.supercoders.superchat.models.registration_request.ReqUser;
import com.internship.supercoders.superchat.models.user_authorization_response.ALog;
import com.internship.supercoders.superchat.models.user_authorization_response.LogAndPas;

import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Points {

    interface AuthorizationPoint {

        @Headers({
                "Content-Type: application/json",
                "QuickBlox-REST-API-Version: 0.1.0"
        })
        @POST("/session.json")
        Call<Session> getSession(@Body Map<String, String> map);

    }

    interface CreateFilePoint {
        @POST("/blobs.json")
        Call<Blob> createFile(@Header("Content-Type") String cont, @Header("QuickBlox-REST-API-Version") String version, @Header("QB-Token") String token, @Body Blob blob);
    }

    interface DeclaringFileUploadedPoint {

        @PUT("/blobs/{id}/complete.json")
        Call<Objects> declaringUpload(@Path("id") int id, @Header("Content-Type") String cont, @Header("QuickBlox-REST-API-Version") String version, @Header("QB-Token") String token, @Body Blob blob);

    }

    interface RegistrationPoint {

        @POST("/users.json")
        Call<Object> registration(@Header("Content-Type") String cont, @Header("QuickBlox-REST-API-Version") String version, @Header("QB-Token") String token, @Body ReqUser user);
    }

    interface ResetPasswordPoint {

        @GET("users/password/reset.json?")
        Call<Object> resetPassword(@Header("Content-Type") String cont, @Header("QB-Token") String token, @Query("email") String email);

    }

    interface SignInPoint {
        @POST("/login.json")
        Call<Objects> signIn(@Header("Content-Type") String cont, @Header("QuickBlox-REST-API-Version") String version, @Header("QB-Token") String token, @Body LogAndPas logAndPas);

    }

    public interface UserAuthorizatoinPoint {
        @Headers({
                "Content-Type: application/json",
                "QuickBlox-REST-API-Version: 0.1.0"
        })
        @POST("/session.json")
        Call<Session> userAuthorizatoin(@Body ALog user);

    }


}
