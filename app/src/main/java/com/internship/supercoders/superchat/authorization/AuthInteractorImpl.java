package com.internship.supercoders.superchat.authorization;

import android.text.TextUtils;
import android.util.Log;

import com.internship.supercoders.superchat.api.ApiClient;
import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.models.user_authorization_response.VerificationData;
import com.internship.supercoders.superchat.models.user_update_request.UpdateUserData;
import com.internship.supercoders.superchat.points.Points;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AuthInteractorImpl implements AuthInteractor {

    AuthView authView;

    AuthInteractorImpl(/*AuthorizationActivity authorizationActivity*/) {
        //this.authView = authorizationActivity;
    }


    @Override
    public void writeUserAuthDataToDB(VerificationData verificationData) {
        // TODO: 1/30/17 [Code Review] NNNNNOOOOOOOOOOOOOO!!!!!!!!!!!!!!!!!!!
        DBMethods db = new DBMethods(authView.getContext());

        db.readFromDB();
        db.writeAuthData(verificationData);
    }

    @Override
    public boolean validateEmail(String email) {
        // TODO: 1/30/17 [Code Review] Due to method name, it shall only return true or false if email is valid
        // (why is this logic in View layer???), but this one also sets error messages. This is wrong.
        // setting error strings = View layer
        // validation = model/interactor layer
        if (TextUtils.isEmpty(email)) {
            authView.setEmailIsEmptyError();
            return false;
        } else {
            if (!email.contains("@")) {
                authView.setEmailIsInvalidError();
                return false;
            }
        }

        authView.hideEmailError();
        return true;
    }

    @Override
    public boolean validatePassword(String password) {
        if (TextUtils.isEmpty(password)) {
            authView.setPasswordIsEmptyError();
            return false;
        }
        authView.hidePasswordError();
        return true;
    }

    @Override
    public boolean isAuthDataValid(String password, String email) {
        boolean isPasswordValid = validatePassword(password);
        boolean isEmailValid = validateEmail(email);
        return isEmailValid && isPasswordValid;
    }

    @Override
    public void signIn(String token, String login, String password, AuthFinishedListener listener) {
        final Points.SignInPoint apiSignIn = ApiClient.getRetrofit().create(Points.SignInPoint.class);
        Call<UpdateUserData> call = apiSignIn.signIn("application/json", "0.1.0", token, new VerificationData(login, password));
        call.enqueue(new Callback<UpdateUserData>() {
            @Override
            public void onResponse(Call<UpdateUserData> call, Response<UpdateUserData> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(token);
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("stas", "signIn error = " + jObjError.getString("errors"));
                        listener.onError("" + jObjError.getString("errors"));
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


}
