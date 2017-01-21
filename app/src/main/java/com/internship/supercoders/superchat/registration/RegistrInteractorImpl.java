package com.internship.supercoders.superchat.registration;


import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.internship.supercoders.superchat.api.ApiCall;
import com.internship.supercoders.superchat.api.ApiClient;
import com.internship.supercoders.superchat.api.RequestBuilder;
import com.internship.supercoders.superchat.models.authorization_response.Session;
import com.internship.supercoders.superchat.models.blob.Blob;
import com.internship.supercoders.superchat.models.blob.BlobData;
import com.internship.supercoders.superchat.models.registration_request.ReqUser;
import com.internship.supercoders.superchat.models.registration_request.ReqUserData;
import com.internship.supercoders.superchat.models.user_authorization_response.ALog;
import com.internship.supercoders.superchat.models.user_authorization_response.LogAndPas;
import com.internship.supercoders.superchat.points.AuthorizationPoint;
import com.internship.supercoders.superchat.points.CreateFilePoint;
import com.internship.supercoders.superchat.points.DeclaringFileUploadedPoint;
import com.internship.supercoders.superchat.points.RegistrationPoint;
import com.internship.supercoders.superchat.points.SignInPoint;
import com.internship.supercoders.superchat.points.UploadFilePoint;
import com.internship.supercoders.superchat.points.UserAuthorizatoinPoint;
import com.internship.supercoders.superchat.tools.HmacSha1Signature;


import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
    public void authorization(final File file, final String email, final String password, final String fullname, final String phone, final String website, final RegistrationFinishedListener listener) {
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


                    registration(file,token,email,password,fullname,phone,website,listener);

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
    public void registration(final File file, final String token, final String email, final String password, String fullname, String phone, String website, final RegistrationFinishedListener listener) {

                final RegistrationPoint apiServRegistr = ApiClient.getRetrofit().create(RegistrationPoint.class);
                Call<Objects> regCall = apiServRegistr.registration("application/json", "0.1.0", token, new ReqUser(new ReqUserData(password,
                        email,fullname,phone,website)));
              regCall.enqueue(new Callback<Objects>() {
                  @Override
                  public void onResponse(Call<Objects> call, Response<Objects> response) {

                      if (response.isSuccessful()) {
                          if(file==null) {
                              listener.onSuccess(token);

                          }else signIn(file,token,email,password,listener);
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
    public void createFile(final File file,final String token, final RegistrationFinishedListener listener) {
        Log.d("stas","createfile");
        String name = randomName();
      final CreateFilePoint apiCreateFile = ApiClient.getRetrofit().create(CreateFilePoint.class);
        Call<Blob> blobCall = apiCreateFile.createFile("application/json","0.1.0",token,new Blob(new BlobData("image/jpeg",name)));
        blobCall.enqueue(new Callback<Blob>() {
            @Override
            public void onResponse(Call<Blob> call, Response<Blob> response) {
                if (response.isSuccessful()) {
                    Blob blob =response.body();
                    String image_id =blob.getBlobData().getId();
                    Log.d("stas","image id = "+image_id );
                    String params =blob.getBlobData().getBlobObjectAccess().getParams();
                    Uri uri =Uri.parse(params);

                    String contentType = uri.getQueryParameter("Content-Type");
                   // Log.d("stas",contentType);
                    String expires = uri.getQueryParameter("Expires");
                   // Log.d("stas",expires);
                    String acl = uri.getQueryParameter("acl");
                    String key = uri.getQueryParameter("key");
                    String policy = uri.getQueryParameter("policy");
                  //  Log.d("stas",policy);
                    String success_action_status = uri.getQueryParameter("success_action_status");
                    String x_amz_algorithm = uri.getQueryParameter("x-amz-algorithm");
                    String x_amz_credential = uri.getQueryParameter("x-amz-credential");
                    String x_amz_date = uri.getQueryParameter("x-amz-date");
                    String x_amz_signature = uri.getQueryParameter("x-amz-signature");
                   // Log.d("stas",x_amz_signature);
                    Log.d("stas","params = "+params);
                    uploadFile(image_id,token, listener, contentType, expires, acl, key, policy, success_action_status, x_amz_algorithm,
                            x_amz_credential, x_amz_date, x_amz_signature, file);







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
                52262, "Mer3vGU4AOrw2zc", randonId, ts, email, password);
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
        Call<Session> call = apiUserAuth.userAuthorizatoin(new ALog(application_id, auth_key, ts, Integer.toString(randonId), signature, new LogAndPas(email, password)));
        call.enqueue(new Callback<Session>() {
            @Override
            public void onResponse(Call<Session> call, Response<Session> response) {
                if (response.isSuccessful()) {
                    Session session = response.body();
                    String token = session.getData().getToken();
                    //createFile(token,listener);

                } else {
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
    public void signIn(final File file,final String token, String email, String password, final RegistrationFinishedListener listener) {
        final SignInPoint apiSignIn = ApiClient.getRetrofit().create(SignInPoint.class);
        Call<Objects> call = apiSignIn.signIn("application/json", "0.1.0", token, new LogAndPas(email, password));
        call.enqueue(new Callback<Objects>() {
            @Override
            public void onResponse(Call<Objects> call, Response<Objects> response) {
                if (response.isSuccessful()) {
                    createFile(file, token, listener);

                } else {
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
    public void uploadFile(final String image_id, final String token, final RegistrationFinishedListener listener, final String contentType, final String expires, final String acl, final String key, final String policy, final String success_action_status,
                           final String x_amz_algorithm, final String x_amz_credential, final String x_amz_date, final String x_amz_signature, final File file) {
        final HttpUrl url = new HttpUrl.Builder().scheme("https").host("qbprod.s3.amazonaws.com").build();
        final OkHttpClient client = new OkHttpClient();

        if (file.exists())
            Log.d("stas", "file exists");


        new AsyncTask<Void, Void, okhttp3.Response>() {


            @Override
            protected okhttp3.Response doInBackground(Void... voids) {
                okhttp3.Response response = null;
                try {
                    Request request = new Request.Builder()
                            .url(url)
                            .post(RequestBuilder.uploadRequestBody(file, contentType, expires, acl, key, policy, success_action_status,
                                    x_amz_algorithm, x_amz_credential, x_amz_date, x_amz_signature))
                            .build();

                    response = client.newCall(request).execute();
                    Log.d("stas", response.body().string() + "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return response;

            }

            @Override
            protected void onPostExecute(okhttp3.Response response) {
                if (response != null) {
                    declaringFileUploaded(image_id, token, file, listener);


                }



                else listener.onError();
            }
        }.execute();

    }

    @Override
    public void declaringFileUploaded(String image_id, final String token, File file, final RegistrationFinishedListener listener) {
        long fileSize = file.length();
        Log.d("stas", "file size = " + Long.toString(fileSize));
        final DeclaringFileUploadedPoint apiDecUpl = ApiClient.getRetrofit().create(DeclaringFileUploadedPoint.class);
        Call<Objects> call = apiDecUpl.declaringUpload(image_id, "application/json", "0.1.0", token, new Blob(new BlobData(Long.toString(fileSize))));
        call.enqueue(new Callback<Objects>() {

            @Override
            public void onResponse(Call<Objects> call, Response<Objects> response) {
                Log.d("stas", response + "");
                if (response.isSuccessful()) {

                    listener.onSuccess(token);
                } else
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("stas", "declaringFileUploaded error = " + jObjError);
                        listener.onError();
                    } catch (Exception e) {
                        Log.d("stas", e.getMessage());
                    }
            }

            @Override
            public void onFailure(Call<Objects> call, Throwable t) {

            }
        });
    }
}






  /*   HashMap<String, String> params = new HashMap<String, String>();
        String url = "https://qbprod.s3.amazonaws.com/";
        String image_path = file.getPath();
        params.put("Content-Type",contentType);
        params.put("Expires",expires);
        params.put("acl",acl);
        params.put("key",key);
        params.put("policy",policy);
        params.put("success_action_status",success_action_status);
        params.put("x-amz-algorithm",x_amz_algorithm);
        params.put("x-amz-credential",x_amz_credential);
        params.put("x-amz-date",x_amz_date);
        params.put("x-amz-signature",x_amz_signature);



        MultipartRequest multipartRequest = new MultipartRequest(url, params, image_path, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("stas", "Success Response: " + response.toString());
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
            });

        RequestQueue requestQueue = Volley.newRequestQueue(listener.getContext());
        requestQueue.add(multipartRequest);
        requestQueue.start();*//*
*/
      /*  final UploadFilePoint apiUpload = ApiClient.getRetrofit(ApiClient.URL_FOR_UPLOAD_AVA).create(UploadFilePoint.class);
        Call<Objects>call = apiUpload.uploadFile( contentType,  expires,  acl,  key,  policy,  success_action_status,
                x_amz_algorithm,  x_amz_credential,  x_amz_date,  x_amz_signature,  file);*/

// final UploadFilePoint apiUpload = ApiClient.getRetrofit(ApiClient.URL_FOR_UPLOAD_AVA).create(UploadFilePoint.class);
//  okhttp3.RequestBody requestFile = okhttp3.RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), file);
//  MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
/*

        RequestBody rcontentType = RequestBody.create(MediaType.parse("text/plain"), contentType);
        RequestBody rexpires = RequestBody.create(MediaType.parse("text/plain"), expires);
        RequestBody racl = RequestBody.create(MediaType.parse("text/plain"), acl);
        RequestBody rkey = RequestBody.create(MediaType.parse("text/plain"), key);
        RequestBody rpolicy = RequestBody.create(MediaType.parse("text/plain"), policy);
        RequestBody rsuccess_action_status = RequestBody.create(MediaType.parse("text/plain"), success_action_status);
        RequestBody rx_amz_algorithm = RequestBody.create(MediaType.parse("text/plain"), x_amz_algorithm);
        RequestBody rx_amz_credential = RequestBody.create(MediaType.parse("text/plain"), x_amz_credential);
        RequestBody rx_amz_date = RequestBody.create(MediaType.parse("text/plain"), x_amz_date);
        RequestBody rx_amz_signature = RequestBody.create(MediaType.parse("text/plain"), x_amz_signature);
        RequestBody rfile = RequestBody.create(MediaType.parse("image/jpeg"), file);

        Call<Objects>call = apiUpload.uploadFile( rcontentType,  rexpires,  racl,  rkey,  rpolicy,  rsuccess_action_status,
                rx_amz_algorithm,  rx_amz_credential,  rx_amz_date,  rx_amz_signature,  rfile);

          Log.d("stas","start upload");
        call.enqueue(new Callback<Objects>() {
            @Override
            public void onResponse(Call<Objects> call, Response<Objects> response) {
                Log.d("stas","onresponse");
                if (response.isSuccessful()) {
                    listener.onSuccess(token);

                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("stas", "uploadFile error = " + jObjError.getString("errors"));
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

    }*/








