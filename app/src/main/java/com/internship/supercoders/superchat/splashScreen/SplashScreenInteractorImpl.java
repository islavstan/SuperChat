package com.internship.supercoders.superchat.splashScreen;

import android.util.Log;

import com.internship.supercoders.superchat.api.ApiClient;
import com.internship.supercoders.superchat.api.ApiConstant;
import com.internship.supercoders.superchat.models.authorization_response.Session;
import com.internship.supercoders.superchat.models.user_authorization_response.ALog;
import com.internship.supercoders.superchat.models.user_authorization_response.LogAndPas;
import com.internship.supercoders.superchat.points.AuthorizationPoint;
import com.internship.supercoders.superchat.points.SignInPoint;
import com.internship.supercoders.superchat.points.UserAuthorizatoinPoint;
import com.internship.supercoders.superchat.utils.HmacSha1Signature;

import org.json.JSONObject;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashScreenInteractorImpl implements SplashScreenInteractor {
    private static final String TAG = "SPLASH";
        //private Long tsLong = System.currentTimeMillis() / 1000;
//    private String ts = tsLong.toString();
//    private int randomId = new Random().nextInt();
    private String signature;

    @Override
    public void userAuthorization(String email, String password, UserAuthorizationFinishedListener authorizationListener) {

        String signatureParams = String.format("application_id=%s&auth_key=%s&nonce=%s&timestamp=%s&user[email]=%s&user[password]=%s",
                ApiConstant.APPLICATION_ID, ApiConstant.AUTH_KEY, ApiConstant.RANDOM_ID, ApiConstant.TS, email, password);
        try {
            signature = HmacSha1Signature.calculateRFC2104HMAC(signatureParams, ApiConstant.AUTH_SECRET);

        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        final UserAuthorizatoinPoint apiUserAuth = ApiClient.getRetrofit().create(UserAuthorizatoinPoint.class);
        Call<Session> call = apiUserAuth.userAuthorizatoin(new ALog(ApiConstant.APPLICATION_ID, ApiConstant.AUTH_KEY, ApiConstant.TS, Integer.toString(ApiConstant.RANDOM_ID), signature, new LogAndPas(email, password)));
        call.enqueue(new Callback<Session>() {
            @Override
            public void onResponse(Call<Session> call, Response<Session> response) {
                if (response.isSuccessful()) {
                    Session session = response.body();
                    String token = session.getData().getToken();
                    authorizationListener.onSuccess(token);


                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("stas", "userAuthorization error = " + jObjError.getString("errors"));
                        authorizationListener.onError();
                    } catch (Exception e) {
                        Log.d("stas", e.getMessage());
                    }

                }


            }

            @Override
            public void onFailure(Call<Session> call, Throwable t) {

            }
        });
    }

    void signIn(String token) {
        Log.d("Splash", "signIn start");
        final SignInPoint apiSignIn = ApiClient.getRetrofit().create(SignInPoint.class);
        Call<Objects> call = apiSignIn.signIn("application/json", "0.1.0", token, new LogAndPas("max@g.com", "testtest"));
        call.enqueue(new Callback<Objects>() {
            @Override
            public void onResponse(Call<Objects> call, Response<Objects> response) {
                if (response.isSuccessful()) {
                    Log.d("Splash", "signIn successes");

                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("Splash", "signIn error = " + jObjError.getString("errors"));

                    } catch (Exception e) {
                        Log.d("stas", e.getMessage());
                    }

                }

            }

            @Override
            public void onFailure(Call<Objects> call, Throwable t) {
                Log.d("Splash", "signIn Failure");
            }
        });
    }
}
    
