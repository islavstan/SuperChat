package com.internship.supercoders.superchat.registration;


import android.util.Log;

import com.internship.supercoders.superchat.api.ApiClient;
import com.internship.supercoders.superchat.models.authorization_response.Session;
import com.internship.supercoders.superchat.models.registration_request.ReqUser;
import com.internship.supercoders.superchat.models.registration_request.ReqUserData;
import com.internship.supercoders.superchat.points.AuthorizationPoint;
import com.internship.supercoders.superchat.points.RegistrationPoint;
import com.internship.supercoders.superchat.tools.HmacSha1Signature;

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

public class RegistrInteractorImpl implements RegistrationInteractor {
    private Long tsLong = System.currentTimeMillis() / 1000;
    private String ts = tsLong.toString();
    private int randonId = new Random().nextInt();
    private String signature;


    @Override
    public void authorization(final String email, final String password, final String fullname, final String phone, final String website, final RegistrationFinishedListener listener) {
        String signatureParams = String.format("application_id=%s&auth_key=%s&nonce=%s&timestamp=%s",
                52262, "Mer3vGU4AOrw2zc", randonId, ts);
        try {
            signature = HmacSha1Signature.calculateRFC2104HMAC(signatureParams, "EeVbNGc82eJZOLS");
            Log.d("stas", "signat = " + signature);
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }


        final AuthorizationPoint apiService = ApiClient.getRetrofit().create(AuthorizationPoint.class);
        Map<String, String> params = new HashMap<>();
        params.put("application_id", "52262");
        params.put("auth_key", "Mer3vGU4AOrw2zc");
        params.put("timestamp", ts);
        Log.d("stas",ts);
        params.put("nonce", Integer.toString(randonId));
        Log.d("stas",randonId+"");
        params.put("signature", signature);
        Log.d("stas",signature);
        Call<Session> call = apiService.getSession(params);
        call.enqueue(new Callback<Session>() {
            @Override
            public void onResponse(Call<Session> call, Response<Session> response) {
                if (response.isSuccessful()) {
                    Session session = response.body();
                  String  token = session.getData().getToken();
                    Log.d("stas", "token = " + token);
                    registration(token,email,password,fullname,phone,website,listener);

                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("stas", "authorization error = " + jObjError.getString("errors"));
                        listener.onError();
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
    public void registration(final String token, String email, String password, String fullname, String phone, String website, final RegistrationFinishedListener listener) {

                final RegistrationPoint apiServRegistr = ApiClient.getRetrofit().create(RegistrationPoint.class);
                Call<Objects> regCall = apiServRegistr.registration("application/json", "0.1.0", token, new ReqUser(new ReqUserData(password,
                        email,fullname,phone,website)));
              regCall.enqueue(new Callback<Objects>() {
                  @Override
                  public void onResponse(Call<Objects> call, Response<Objects> response) {

                      if (response.isSuccessful()) {
                          listener.onSuccess(token);
                      }else {
                          try {
                              JSONObject jObjError = new JSONObject(response.errorBody().string());
                              Log.d("stas", "authorization error = " + jObjError.getString("errors"));
                          } catch (Exception e) {
                              Log.d("stas", e.getMessage());
                          }

                      }
                  }

                  @Override
                  public void onFailure(Call<Objects> call, Throwable t) {

                  }
              });


    }
}
