package com.internship.supercoders.superchat.authorization;

import android.widget.EditText;

import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.models.user_authorization_response.VerificationData;

/**
 * Created by RON on 05.02.2017.
 */
public interface AuthPresenter {

    void onError();

    void onSuccess(String token);

    void onDestroy();

    void changePassword();

    void validateData(VerificationData verificationData);

    void signIn(DBMethods dbMethods, String token, String login, String password);

   void validateUserInfo(EditText email, EditText password);
    void unsubscribe();


}
