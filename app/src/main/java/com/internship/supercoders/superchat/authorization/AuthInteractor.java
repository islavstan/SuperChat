package com.internship.supercoders.superchat.authorization;

import com.internship.supercoders.superchat.models.user_authorization_response.VerificationData;


public interface AuthInteractor {


    interface AuthFinishedListener {
        void onSuccess(String token);

        void onError(String error);

    }

    // TODO: 1/30/17 [Code Review] This is a part of business logic, move it to interactors/model layer
    void writeUserAuthDataToDB(VerificationData verificationData);

    // TODO: 1/30/17 [Code Review] This is a part of business logic, move it to interactors/model layer
    boolean validateEmail(String email);

    // TODO: 1/30/17 [Code Review] This is a part of business logic, move it to interactors/model layer
    boolean validatePassword(String password);

    boolean isAuthDataValid(String password, String email);

    void signIn(String token, String login, String password, AuthFinishedListener authFinishedListener);
}
