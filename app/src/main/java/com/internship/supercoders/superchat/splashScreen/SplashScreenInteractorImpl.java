package com.internship.supercoders.superchat.splashScreen;

import android.util.Log;

import com.internship.supercoders.superchat.api.ApiClient;
import com.internship.supercoders.superchat.api.ApiConstant;
import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.models.authorization_response.Session;
import com.internship.supercoders.superchat.models.user_authorization_response.ALog;
import com.internship.supercoders.superchat.models.user_authorization_response.LogAndPas;
import com.internship.supercoders.superchat.points.Points;
import com.internship.supercoders.superchat.utils.HmacSha1Signature;
import com.internship.supercoders.superchat.utils.UserPreferences;

import org.json.JSONObject;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashScreenInteractorImpl implements SplashScreenInteractor {
    private static final String TAG = "SPLASH";
    //private Long tsLong = System.currentTimeMillis() / 1000;
//    private String ts = tsLong.toString();
//    private int randomId = new Random().nextInt();
    private String signature;
    private DBMethods dbManager;
    private UserPreferences userPreferences;

    public SplashScreenInteractorImpl(DBMethods dbManager, UserPreferences userPreferences) {
        this.dbManager = dbManager;
        this.userPreferences = userPreferences;
    }

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
        final Points.UserAuthorizatoinPoint apiUserAuth = ApiClient.getRetrofit().create(Points.UserAuthorizatoinPoint.class);
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

    @Override
    public void authorization(UserAuthorizationFinishedListener authorizationListener) {
        String signatureParams = String.format("application_id=%s&auth_key=%s&nonce=%s&timestamp=%s",
                ApiConstant.APPLICATION_ID, ApiConstant.AUTH_KEY, ApiConstant.RANDOM_ID, ApiConstant.TS);
        try {
            signature = HmacSha1Signature.calculateRFC2104HMAC(signatureParams, ApiConstant.AUTH_SECRET);
            Log.d("stas", "signat = " + signature);
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }


        final Points.AuthorizationPoint apiService = ApiClient.getRetrofit().create(Points.AuthorizationPoint.class);
        Map<String, String> params = new HashMap<>();
        params.put("application_id", ApiConstant.APPLICATION_ID);
        params.put("auth_key", ApiConstant.AUTH_KEY);
        params.put("timestamp", ApiConstant.TS);
        params.put("nonce", Integer.toString(ApiConstant.RANDOM_ID));
        params.put("signature", signature);
        Call<Session> call = apiService.getSession(params);
        call.enqueue(new Callback<Session>() {
            @Override
            public void onResponse(Call<Session> call, Response<Session> response) {
                if (response.isSuccessful()) {
                    Session session = response.body();
                    String token = session.getData().getToken();
                    Log.d("stas", "token = " + token);
                    authorizationListener.onSuccess(token);
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("stas", "authorization error = " + jObjError.getString("errors"));
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

    @Override
    public boolean isAuth() {
        return userPreferences.isUserSignedIn();
    }

    @Override
    public LogAndPas getUserInfo() {
        return dbManager.getAuthData();
    }
}
    
