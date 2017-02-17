package com.internship.supercoders.superchat.registration;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.internship.supercoders.superchat.api.ApiClient;
import com.internship.supercoders.superchat.api.ApiConstant;
import com.internship.supercoders.superchat.api.RequestBuilder;
import com.internship.supercoders.superchat.models.authorization_response.Session;
import com.internship.supercoders.superchat.models.blob.Blob;
import com.internship.supercoders.superchat.models.blob.BlobData;
import com.internship.supercoders.superchat.models.registration_request.ReqUser;
import com.internship.supercoders.superchat.models.registration_request.ReqUserData;
import com.internship.supercoders.superchat.models.user_authorization_response.ALog;
import com.internship.supercoders.superchat.models.user_authorization_response.VerificationData;
import com.internship.supercoders.superchat.models.user_update_request.UpdateUser;
import com.internship.supercoders.superchat.models.user_update_request.UpdateUserData;
import com.internship.supercoders.superchat.points.Points;
import com.internship.supercoders.superchat.utils.HmacSha1Signature;
import com.jakewharton.rxbinding.widget.RxTextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func3;
import rx.subscriptions.CompositeSubscription;

public class RegistrInteractorImpl implements RegistrationInteractor {

    private String signature;
    Matcher matcher;
    private Pattern pattern = android.util.Patterns.EMAIL_ADDRESS;
    CompositeSubscription compositeSubscription;

    public RegistrInteractorImpl() {

        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void authorization(String token, final File file, final String email, final String password, final String fullname, final String phone, final String website, String facebookId, final RegistrationFinishedListener listener) {
        if (token != null) {
            registration(file, token, email, password, fullname, phone, website, facebookId, listener);
        } else {


            String signatureParams = String.format("application_id=%s&auth_key=%s&nonce=%s&timestamp=%s",
                    ApiConstant.APPLICATION_ID, ApiConstant.AUTH_KEY, ApiConstant.RANDOM_ID, ApiConstant.TS);
            try {
                signature = HmacSha1Signature.calculateRFC2104HMAC(signatureParams, ApiConstant.AUTH_SECRET);
//            Log.d("stas", "signat = " + signature);
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
            Log.d("stas", ApiConstant.TS);
            params.put("nonce", Integer.toString(ApiConstant.RANDOM_ID));
            Log.d("stas", ApiConstant.RANDOM_ID + "");
            params.put("signature", signature);
            Log.d("stas", signature);
            Call<Session> call = apiService.getSession(params);
            call.enqueue(new Callback<Session>() {
                @Override
                public void onResponse(Call<Session> call, Response<Session> response) {
                    if (response.isSuccessful()) {
                        Session session = response.body();
                        String token = session.getData().getToken();
                        Log.d("stas", "token = " + token);


                        registration(file, token, email, password, fullname, phone, website, facebookId, listener);

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            // Log.d("stas", "signInUser error = " + jObjError.getString("errors"));
                            listener.onError();
                        } catch (Exception e) {
                            // Log.d("stas", e.getMessage());
                        }

                    }
                }

                @Override
                public void onFailure(Call<Session> call, Throwable t) {

                }
            });
        }

    }


    @Override
    public void facebookLogin(LoginButton logBtn, CallbackManager callbackManager, RegistrationFinishedListener listener) {
        logBtn.performClick();
        logBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String accessToken = loginResult.getAccessToken()
                        .getToken();
                Log.d("stas", "accessToken = " + accessToken);
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        (object, response) -> {

                            try {
                                String facebookId = object.getString("id");
                                listener.onSuccessFacebookLogin(facebookId);
                                Log.d("stas", "id = " + facebookId);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields",
                        "id");
                request.setParameters(parameters);
                request.executeAsync();
            }


            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }


    @Override
    public void registration(final File file, final String token, final String email, final String password, String fullname, String phone, String website, String facebookId, final RegistrationFinishedListener listener) {

        final Points.RegistrationPoint apiServRegistr = ApiClient.getRetrofit().create(Points.RegistrationPoint.class);
        Call<UpdateUser> regCall = apiServRegistr.registration("application/json", "0.1.0", token, new ReqUser(new ReqUserData(password,
                email, fullname, phone, website, facebookId)));
        regCall.enqueue(new Callback<UpdateUser>() {
            @Override
            public void onResponse(Call<UpdateUser> call, Response<UpdateUser> response) {

                if (response.isSuccessful()) {
                    UpdateUser user = response.body();
                    String userId = user.getUpdateUserData().getId();

                    if (file == null) {
                        listener.onSuccess(token);

                    } else signIn(userId, file, token, email, password, listener);
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("stas", "registration error = " + jObjError.getString("errors"));

                        String jError = jObjError.getString("errors");
                        String error = jError.replaceAll("[{\"}]", "").replace("[", "").replace("]", "").replace(":"," : ");

                        Log.d("stas", "new error = " + error);

                        listener.onErrorWithToken(token, error);
                    } catch (Exception e) {
                        Log.d("stas", e.getMessage());
                    }

                }
            }

            @Override
            public void onFailure(Call<UpdateUser> call, Throwable t) {

            }
        });


    }

    public String randomName() {
        Random r = new Random(); // just create one and keep it around
        String alphabet = "abcdefghijklmnopqrstuvwxyz";

        final int N = 10;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            sb.append(alphabet.charAt(r.nextInt(alphabet.length())));
        }
        String randomName = sb.toString();

        return randomName + ".jpeg";
    }


    @Override
    public void createFile(String userId, final File file, final String token, final RegistrationFinishedListener listener) {
        Log.d("stas", "createfile");
        String name = randomName();
        final Points.CreateFilePoint apiCreateFile = ApiClient.getRetrofit().create(Points.CreateFilePoint.class);
        Call<Blob> blobCall = apiCreateFile.createFile("application/json", "0.1.0", token, new Blob(new BlobData("image/jpeg", name)));
        blobCall.enqueue(new Callback<Blob>() {
            @Override
            public void onResponse(Call<Blob> call, Response<Blob> response) {
                if (response.isSuccessful()) {
                    Blob blob = response.body();
                    String image_id = blob.getBlobData().getId();
                    Log.d("stas", "image id = " + image_id);
                    String params = blob.getBlobData().getBlobObjectAccess().getParams();
                    Uri uri = Uri.parse(params);

                    String contentType = uri.getQueryParameter("Content-Type");
                    Log.d("stas", contentType);
                    String expires = uri.getQueryParameter("Expires");
                    Log.d("stas", expires);
                    String acl = uri.getQueryParameter("acl");
                    String key = uri.getQueryParameter("key");
                    String policy = uri.getQueryParameter("policy");
                    Log.d("stas", policy);
                    String success_action_status = uri.getQueryParameter("success_action_status");
                    String x_amz_algorithm = uri.getQueryParameter("x-amz-algorithm");
                    String x_amz_credential = uri.getQueryParameter("x-amz-credential");
                    String x_amz_date = uri.getQueryParameter("x-amz-date");
                    String x_amz_signature = uri.getQueryParameter("x-amz-signature");
                    // Log.d("stas",x_amz_signature);
                    Log.d("stas", "params = " + params);
                    uploadFile(userId, image_id, token, listener, contentType, expires, acl, key, policy, success_action_status, x_amz_algorithm,
                            x_amz_credential, x_amz_date, x_amz_signature, file);


                } else {
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
                ApiConstant.APPLICATION_ID, ApiConstant.AUTH_KEY, ApiConstant.RANDOM_ID, ApiConstant.TS, email, password);
        try {
            signature = HmacSha1Signature.calculateRFC2104HMAC(signatureParams, "EeVbNGc82eJZOLS");

        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        final Points.UserAuthorizatoinPoint apiUserAuth = ApiClient.getRetrofit().create(Points.UserAuthorizatoinPoint.class);
        Call<Session> call = apiUserAuth.userAuthorizatoin(new ALog(ApiConstant.APPLICATION_ID, ApiConstant.AUTH_KEY, ApiConstant.TS, Integer.toString(ApiConstant.RANDOM_ID), signature, new VerificationData(email, password)));
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
    public void signIn(String userId, final File file, final String token, String email, String password, final RegistrationFinishedListener listener) {
        final Points.SignInPoint apiSignIn = ApiClient.getRetrofit().create(Points.SignInPoint.class);
        Call<UpdateUserData> call = apiSignIn.signIn("application/json", "0.1.0", token, new VerificationData(email, password));
        call.enqueue(new Callback<UpdateUserData>() {
            @Override
            public void onResponse(Call<UpdateUserData> call, Response<UpdateUserData> response) {
                if (response.isSuccessful()) {
                    createFile(userId, file, token, listener);

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
            public void onFailure(Call<UpdateUserData> call, Throwable t) {

            }
        });
    }


    @Override
    public void uploadFile(String userId, final String image_id, final String token, final RegistrationFinishedListener listener, final String contentType, final String expires, final String acl, final String key, final String policy, final String success_action_status,
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
                    long fileSize = file.length();
                    Log.d("stas", "file size = " + Long.toString(fileSize));
                    declaringFileUploaded(userId, image_id, token, file, listener);


                } else listener.onError();
            }
        }.execute();

    }

    @Override
    public void declaringFileUploaded(String userId, String image_id, final String token, File file, final RegistrationFinishedListener listener) {
        long fileSize = file.length();
        Log.d("stas", "file size = " + Long.toString(fileSize));
        final Points.DeclaringFileUploadedPoint apiDecUpl = ApiClient.getRetrofit().create(Points.DeclaringFileUploadedPoint.class);
        Call<Void> call = apiDecUpl.declaringUpload(Integer.parseInt(image_id), "application/json", "0.1.0", token, new Blob(new BlobData((int) fileSize)));


        call.enqueue(new Callback<Void>() {


            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("stas", response + "");
                if (response.isSuccessful()) {
                  updateUserAva(image_id, userId,token, listener);

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
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("stas", "onFailure = " + t.getMessage());

            }
        });


    }

    @Override
    public void updateUserAva(String image_id, String userId, String token, RegistrationFinishedListener listener) {
        final Points.UpdateUserPoint updateUserPoint = ApiClient.getRetrofit().create(Points.UpdateUserPoint.class);
        Call<UpdateUser> call = updateUserPoint.update(userId, "application/json", "0.1.0", token, new UpdateUser(new UpdateUserData(image_id)));
        call.enqueue(new Callback<UpdateUser>() {
            @Override
            public void onResponse(Call<UpdateUser> call, Response<UpdateUser> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(token);
                } else
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("stas", "updateUserAva error = " + jObjError);
                        listener.onError();
                    } catch (Exception e) {
                        Log.d("stas", e.getMessage());
                    }
            }

            @Override
            public void onFailure(Call<UpdateUser> call, Throwable t) {

            }
        });


    }

    @Override
    public void validateUserInfo(EditText emailET, EditText passwordET, EditText confPassET, Button signupBtn, RegistrationFinishedListener listener) {

        Observable<CharSequence> emailChangeObservable = RxTextView.textChanges(emailET);
        Observable<CharSequence> passwordChangeObservable = RxTextView.textChanges(passwordET);
        Observable<CharSequence> confirmPassChangeObservable = RxTextView.textChanges(confPassET);

        Subscription confirmPasswordSubscrioption = confirmPassChangeObservable.doOnNext(next -> listener.hideError(4))
                .debounce(400, TimeUnit.MILLISECONDS)
                .filter(charSequence -> !TextUtils.isEmpty(charSequence))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(charSequence -> {

                    boolean isPasswordValid = validatePassword(charSequence.toString());
                    if (charSequence.toString().length() < 8) {
                        listener.showPasswordLengthError(2);
                    } else if (!isPasswordValid) {
                        listener.showPasswordError(2);
                    } else if (!charSequence.toString().equals(passwordET.getText().toString())) {
                        listener.showConfirmPasswordError();
                    } else {
                        listener.hideError(4);
                    }
                });

        Subscription passwordSubscrioption = passwordChangeObservable.doOnNext(next -> listener.hideError(2))
                .debounce(400, TimeUnit.MILLISECONDS)
                .filter(charSequence -> !TextUtils.isEmpty(charSequence))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(charSequence -> {
                    boolean isPasswordValid = validatePassword(charSequence.toString());
                    if (charSequence.toString().length() < 8) {
                        listener.showPasswordLengthError(1);
                    } else if (!isPasswordValid) {
                        listener.showPasswordError(1);
                    } else {
                        listener.hideError(2);
                    }


                });

        Subscription emailSubscription = emailChangeObservable
                .doOnNext(next -> listener.hideError(1))
                .debounce(400, TimeUnit.MILLISECONDS)
                .filter(charSequence -> !TextUtils.isEmpty(charSequence))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(charSequence -> {

                    boolean isEmailValid = validateEmail(charSequence.toString());
                    if (!isEmailValid) {
                        listener.showEmailError();
                    } else {
                        listener.hideError(1);
                    }

                });


        Subscription signInFieldsSubscription = Observable.combineLatest(emailChangeObservable, passwordChangeObservable, confirmPassChangeObservable, new Func3<CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence email, CharSequence password, CharSequence confPass) {
                boolean isEmailValid = validateEmail(email.toString());
                boolean isPasswordLengthValid = password.toString().length() >= 8;
                boolean isPasswordValid = validatePassword(password.toString());
                boolean isConfirmPasswordLengthValid = confPass.toString().length() >= 8;
                boolean isConfirmPasswordValid = validatePassword(confPass.toString());
                boolean isSamePass = password.toString().equals(confPass.toString());


                return isEmailValid && isPasswordLengthValid && isPasswordValid && isConfirmPasswordLengthValid && isConfirmPasswordValid && isSamePass;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(validFields -> {

                    if (validFields) {
                        listener.enableSignUp();
                    } else {
                        listener.disableSignUp();
                    }


                });

        compositeSubscription.add(confirmPasswordSubscrioption);
        compositeSubscription.add(passwordSubscrioption);
        compositeSubscription.add(emailSubscription);
        compositeSubscription.add(signInFieldsSubscription);


    }

    @Override
    public boolean validatePassword(String password) {
        if (TextUtils.isEmpty(password))
            return false;
        final Pattern pattern = Pattern.compile("^(?=.{8,12}$)(?=(.*[A-Z]){2})(?=(.*[a-z]){0,})(?=(.*[0-9]){2})(?=\\S+$).*$");
        matcher = pattern.matcher(password);
        return matcher.matches();
    }


    @Override
    public boolean validateEmail(String email) {
        if (TextUtils.isEmpty(email))
            return false;
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public void unsubscribe() {
        compositeSubscription.unsubscribe();
    }

    @Override
    public void makePhotoFromCamera(Intent data, File dir, RegistrationFinishedListener listener) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.PNG, 0, bytes);
        File destination = new File(dir,
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.flush();
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        listener.setPhotoFromCamera(destination, thumbnail);

    }







}







