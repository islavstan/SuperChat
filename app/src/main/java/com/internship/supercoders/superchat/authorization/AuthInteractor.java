package com.internship.supercoders.superchat.authorization;

import android.widget.EditText;

import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.models.user_authorization_response.VerificationData;


public interface AuthInteractor {


    interface AuthFinishedListener {
        void onSuccess(String token);

        void hideError(int item);

        void onError(String error);

        void enableLogin();

        void disableLogin();

        void showEmailError();

        void showPasswordLengthError();

        void showPasswordError();

    }

    // TODO: 1/30/17 [Code Review] This is a part of business logic, move it to interactors/model layer
    void writeUserAuthDataToDB(VerificationData verificationData);

    // TODO: 1/30/17 [Code Review] This is a part of business logic, move it to interactors/model layer
    boolean validateEmail(String email);

    // TODO: 1/30/17 [Code Review] This is a part of business logic, move it to interactors/model layer
    boolean validatePassword(String password);

    boolean isAuthDataValid(String password, String email);

    void signIn(DBMethods dbMethods, String token, String login, String password, AuthFinishedListener authFinishedListener);

    void downloadUserPhoto(DBMethods db, String userId, String blobId, String token, AuthFinishedListener listener);

    void userAthorization(DBMethods db, String password, String email, AuthFinishedListener listener);

    void validateUserInfo(EditText email, EditText password, AuthFinishedListener listener);

    void unsubscribe();

}
