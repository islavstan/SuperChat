package com.internship.supercoders.superchat.splash_screen;

//import android.util.Log;

import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.api.ApiClient;
import com.internship.supercoders.superchat.api.ApiConstant;
import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.models.authorization_response.Session;
import com.internship.supercoders.superchat.models.user_authorization_response.ALog;
import com.internship.supercoders.superchat.models.user_authorization_response.VerificationData;
import com.internship.supercoders.superchat.models.user_update_request.UpdateUser;
import com.internship.supercoders.superchat.points.Points;
import com.internship.supercoders.superchat.utils.HmacSha1Signature;
import com.internship.supercoders.superchat.utils.UserPreferences;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class SplashScreenInteractorImpl implements SplashScreenInteractor {
    private String signature;
    private DBMethods dbManager;
    private UserPreferences userPreferences;

    public SplashScreenInteractorImpl(DBMethods dbManager, UserPreferences userPreferences) {
        this.dbManager = dbManager;
        this.userPreferences = userPreferences;
    }

    @Override
    public Observable<Session> userAuthorization(String email, String password) {
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

        final Points.RxUserAuthorizationPoint apiUserAuth = ApiClient.getRxRetrofit().create(Points.RxUserAuthorizationPoint.class);
        return apiUserAuth.rxUserAuthorizatoin(new ALog(ApiConstant.APPLICATION_ID, ApiConstant.AUTH_KEY, ApiConstant.TS, Integer.toString(ApiConstant.RANDOM_ID), signature, new VerificationData(email, password)));
    }



    @Override
    public  Observable<UpdateUser> signIn(String token) {
        final Points.SignInPoint2 signInPoint = ApiClient.getRxRetrofit().create(Points.SignInPoint2.class);
        return signInPoint.rxSignIn("application/json", "0.1.0", token, new VerificationData(dbManager.getEmail(), dbManager.getPassword()));



      /*  final Points.SignInPoint apiSignIn = ApiClient.getRetrofit().create(Points.SignInPoint.class);
        Call<UpdateUserData> call = apiSignIn.signIn("application/json", "0.1.0", token, new VerificationData(email, password));
        call.enqueue(new Callback<UpdateUserData>() {
            @Override
            public void onResponse(Call<UpdateUserData> call, Response<UpdateUserData> response) {
                if (response.isSuccessful()) {

                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("stas", "SplashScreen signIn error = " + jObjError.getString("errors"));

                    } catch (Exception e) {
                        Log.d("stas", e.getMessage());
                    }

                }

            }

            @Override
            public void onFailure(Call<UpdateUserData> call, Throwable t) {

            }
        });*/
    }


    @Override
    public boolean isAuth()
    {
        return dbManager.isLoginUser();
    }

    @Override
    public VerificationData getUserInfo() {
//        final VerificationData[] verificationData = new VerificationData[1];
//        dbManager.getEmailAndPassword().subscribe(data -> verificationData[0] = data);
//        return verificationData[0];

        return new VerificationData(dbManager.getEmail(), dbManager.getPassword());

    }

    @Override
    public void saveToken(String token) {
        dbManager.writeToken(token);

    }

    @Override
    public Observable<Session> createSession() {
        String signatureParams = String.format("application_id=%s&auth_key=%s&nonce=%s&timestamp=%s",
                ApiConstant.APPLICATION_ID, ApiConstant.AUTH_KEY, ApiConstant.RANDOM_ID, ApiConstant.TS);
        try {
            signature = HmacSha1Signature.calculateRFC2104HMAC(signatureParams, ApiConstant.AUTH_SECRET);
            //Log.d("stas", "signat = " + signature);
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }


        final Points.AuthorizationPoint apiService = ApiClient.getRxRetrofit().create(Points.AuthorizationPoint.class);
        Map<String, String> params = new HashMap<>();
        params.put("application_id", ApiConstant.APPLICATION_ID);
        params.put("auth_key", ApiConstant.AUTH_KEY);
        params.put("timestamp", ApiConstant.TS);
        params.put("nonce", Integer.toString(ApiConstant.RANDOM_ID));
        params.put("signature", signature);
        return apiService.getRxSession(params);

    }

    @Override
    public String getToken() {
        return dbManager.getToken();
    }
}
    
