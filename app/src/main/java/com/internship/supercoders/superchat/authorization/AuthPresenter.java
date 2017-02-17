package com.internship.supercoders.superchat.authorization;

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

    void signIn(String token, String login, String password);
}
