package com.internship.supercoders.superchat.forgot_password;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.api.ApiClient;
import com.internship.supercoders.superchat.api.ApiConstant;
import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.models.authorization_response.Session;
import com.internship.supercoders.superchat.models.user_authorization_response.VerificationData;
import com.internship.supercoders.superchat.points.Points;
import com.internship.supercoders.superchat.utils.HmacSha1Signature;


import org.json.JSONObject;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ForgotPasswordDialog extends DialogFragment {


    Context context;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        context = getActivity();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.getActivity());
        LayoutInflater inflater = this.getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_change_password, null);
        dialogBuilder.setView(dialogView);
        EditText email = (EditText) dialogView.findViewById(R.id.emailET);
        dialogBuilder.setTitle(getResources().getString(R.string.title));
        dialogBuilder.setPositiveButton(getResources().getString(R.string.send), (dialogInterface, i) -> {

            authorization(email);

        }).setNegativeButton(getResources().getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss());


        final AlertDialog dialog = dialogBuilder.create();

        return dialog;
    }


    private void showToast() {
        Toast.makeText(context, "Check your email, we sent letter for you", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
    }


    private void authorization(EditText email) {
        String signature = null;
        String signatureParams = String.format("application_id=%s&auth_key=%s&nonce=%s&timestamp=%s",
                ApiConstant.APPLICATION_ID, ApiConstant.AUTH_KEY, ApiConstant.RANDOM_ID, ApiConstant.TS);
        try {
            signature = HmacSha1Signature.calculateRFC2104HMAC(signatureParams, ApiConstant.AUTH_SECRET);
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

                    changePassword(email, token);


                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("stas", "authorization error = " + jObjError.getString("errors"));

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


    void changePassword(EditText emailET, String token) {
        String email = emailET.getText().toString().trim();
        final Points.ResetPasswordPoint apiResetPassword = ApiClient.getRetrofit().create(Points.ResetPasswordPoint.class);
        Call<Void> call = apiResetPassword.resetPassword("application/json", token, email);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.isSuccessful()) {
                    destroySession(token);

                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("stas", "changePassword error = " + jObjError.getString("errors"));

                    } catch (Exception e) {
                        Log.d("stas", e.getMessage());
                    }

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("stas", "changePassword onFailure = " + t.getMessage());
            }
        });


    }


    void destroySession(String token) {
        final Points.SignOut signOut = ApiClient.getRxRetrofit().create(Points.SignOut.class);
        signOut.destroySession(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isSuccessful()) {
                        showToast();


                    }
                }, error -> Log.d("stas", "destroySession error = " + error.getMessage()));

    }
}
