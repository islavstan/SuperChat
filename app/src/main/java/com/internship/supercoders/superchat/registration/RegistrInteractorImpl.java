package com.internship.supercoders.superchat.registration;


import android.net.Uri;
import android.util.Log;

import com.internship.supercoders.superchat.api.ApiClient;
import com.internship.supercoders.superchat.models.authorization_response.Session;
import com.internship.supercoders.superchat.models.blob.Blob;
import com.internship.supercoders.superchat.models.blob.BlobData;
import com.internship.supercoders.superchat.models.registration_request.ReqUser;
import com.internship.supercoders.superchat.models.registration_request.ReqUserData;
import com.internship.supercoders.superchat.models.user_authorization_response.ALog;
import com.internship.supercoders.superchat.models.user_authorization_response.LogAndPas;
import com.internship.supercoders.superchat.points.AuthorizationPoint;
import com.internship.supercoders.superchat.points.CreateFilePoint;
import com.internship.supercoders.superchat.points.RegistrationPoint;
import com.internship.supercoders.superchat.points.SignInPoint;
import com.internship.supercoders.superchat.points.UserAuthorizatoinPoint;
import com.internship.supercoders.superchat.tools.HmacSha1Signature;

import org.json.JSONObject;

import java.io.File;
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
    final String application_id = "52262";
    final String auth_key = "Mer3vGU4AOrw2zc";


    @Override
    public void authorization(final boolean image, final String email, final String password, final String fullname, final String phone, final String website, final RegistrationFinishedListener listener) {
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
        params.put("application_id",application_id);
        params.put("auth_key",auth_key );
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

                    Log.d("stas","bool image = "+image);
                    registration(image,token,email,password,fullname,phone,website,listener);

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
    public void registration(final boolean image, final String token, final String email, final String password, String fullname, String phone, String website, final RegistrationFinishedListener listener) {

                final RegistrationPoint apiServRegistr = ApiClient.getRetrofit().create(RegistrationPoint.class);
                Call<Objects> regCall = apiServRegistr.registration("application/json", "0.1.0", token, new ReqUser(new ReqUserData(password,
                        email,fullname,phone,website)));
              regCall.enqueue(new Callback<Objects>() {
                  @Override
                  public void onResponse(Call<Objects> call, Response<Objects> response) {

                      if (response.isSuccessful()) {
                          if(!image) {
                              listener.onSuccess(token);

                          }else /*userAuthorization(email,password,listener);*/signIn(token,email,password,listener);
                      }
                      else {
                          try {
                              JSONObject jObjError = new JSONObject(response.errorBody().string());
                              Log.d("stas", "registration error = " + jObjError.getString("errors"));
                              listener.onError();
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

    public String randomName(){
        Random r = new Random(); // just create one and keep it around
        String alphabet = "abcdefghijklmnopqrstuvwxyz";

        final int N = 10;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            sb.append(alphabet.charAt(r.nextInt(alphabet.length())));
        }
        String randomName = sb.toString();

       return randomName+".jpeg";
    }


    @Override
    public void createFile(final String token, final RegistrationFinishedListener listener) {
        Log.d("stas","createfile");
        String name = randomName();
      final CreateFilePoint apiCreateFile = ApiClient.getRetrofit().create(CreateFilePoint.class);
        Call<Blob> blobCall = apiCreateFile.createFile("application/json","0.1.0",token,new Blob(new BlobData("image/jpeg",name)));
        blobCall.enqueue(new Callback<Blob>() {
            @Override
            public void onResponse(Call<Blob> call, Response<Blob> response) {
                if (response.isSuccessful()) {
                    Blob blob =response.body();
                    String params =blob.getBlobData().getBlobObjectAccess().getParams();
                    Uri uri =Uri.parse(params);

                    String contentType = uri.getQueryParameter("Content-Type");
                    String expires = uri.getQueryParameter("Expires");
                    String acl = uri.getQueryParameter("acl");
                    String key = uri.getQueryParameter("key");
                    String policy = uri.getQueryParameter("policy");
                    String success_action_status = uri.getQueryParameter("success_action_status");
                    String x_amz_algorithm = uri.getQueryParameter("x-amz-algorithm");
                    String x_amz_credential = uri.getQueryParameter("x-amz-credential");
                    String x_amz_date = uri.getQueryParameter("x-amz-date");
                    String x_amz_signature = uri.getQueryParameter("x-amz-signature");

                 /*   uploadFile(contentType,expires,acl,key,policy,success_action_status,x_amz_algorithm,
                            x_amz_credential,x_amz_date,x_amz_signature,);*/





                    Log.d("stas","params = "+params);
                    listener.onSuccess(token);
                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("stas", "createFile error = " + jObjError.getString("errors"));

                    } catch (Exception e) {
                        Log.d("stas", e.getMessage());
                    }

                }
            }

            @Override
            public void onFailure(Call<Blob> call, Throwable t) {

            }
        });
    }

    @Override
    public void userAuthorization(String email, String password, final RegistrationFinishedListener listener) {
        String signatureParams = String.format("application_id=%s&auth_key=%s&nonce=%s&timestamp=%s&user[email]=%s&user[password]=%s",
                52262, "Mer3vGU4AOrw2zc", randonId, ts,email,password);
        try {
            signature = HmacSha1Signature.calculateRFC2104HMAC(signatureParams, "EeVbNGc82eJZOLS");

        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        final UserAuthorizatoinPoint apiUserAuth = ApiClient.getRetrofit().create(UserAuthorizatoinPoint.class);
        Call<Session>call = apiUserAuth.userAuthorizatoin(new ALog(application_id,auth_key,ts,Integer.toString(randonId),signature,new LogAndPas(email,password)));
        call.enqueue(new Callback<Session>() {
            @Override
            public void onResponse(Call<Session> call, Response<Session> response) {
                if (response.isSuccessful()) {
                    Session session =response.body();
                    String token =session.getData().getToken();
                    createFile(token,listener);

                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("stas", "userAuthorization error = " + jObjError.getString("errors"));
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
    public void signIn(final String token, String email, String password, final RegistrationFinishedListener listener) {
        final SignInPoint apiSignIn = ApiClient.getRetrofit().create(SignInPoint.class);
        Call<Objects>call =apiSignIn.signIn("application/json","0.1.0",token,new LogAndPas(email,password));
        call.enqueue(new Callback<Objects>() {
            @Override
            public void onResponse(Call<Objects> call, Response<Objects> response) {
                if (response.isSuccessful()) {
                    createFile(token,listener);

                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("stas", "signIn error = " + jObjError.getString("errors"));
                        listener.onError();
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

    @Override
    public void uploadFile(String contentType, String expires, String acl, String key, String policy, String success_action_status, String x_amz_algorithm, String x_amz_credential, String x_amz_date, String x_amz_signature, File file) {

    }
}
