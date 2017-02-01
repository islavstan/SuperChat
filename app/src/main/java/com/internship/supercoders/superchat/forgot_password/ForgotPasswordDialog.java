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
import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.models.user_authorization_response.VerificationData;
import com.internship.supercoders.superchat.points.Points;


import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForgotPasswordDialog extends DialogFragment {

    private DBMethods db;
    Context context;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        db = new DBMethods(getActivity());
        context = getActivity();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.getActivity());
        LayoutInflater inflater = this.getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_change_password, null);
        dialogBuilder.setView(dialogView);
        EditText email = (EditText) dialogView.findViewById(R.id.emailET);
        dialogBuilder.setTitle(getResources().getString(R.string.title));
        dialogBuilder.setPositiveButton(getResources().getString(R.string.send), (dialogInterface, i) -> {

            changePassword(email);

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


    void changePassword(EditText emailET) {
        String email = emailET.getText().toString().trim();
        String token = db.getToken();
        final Points.ResetPasswordPoint apiResetPassword = ApiClient.getRetrofit().create(Points.ResetPasswordPoint.class);
        Call<Void> call = apiResetPassword.resetPassword("application/json", token, email);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.isSuccessful()) {
                    showToast();

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
}
