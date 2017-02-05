package com.internship.supercoders.superchat.authorization;

import com.internship.supercoders.superchat.models.user_authorization_response.VerificationData;

/**
 * Created by RON on 05.02.2017.
 */
public interface AuthInteractor {

    // TODO: 1/30/17 [Code Review] This is a part of business logic, move it to interactors/model layer
    void writeUserAuthDataToDB(VerificationData verificationData);

    // TODO: 1/30/17 [Code Review] This is a part of business logic, move it to interactors/model layer
    boolean validateEmail(String email);

    // TODO: 1/30/17 [Code Review] This is a part of business logic, move it to interactors/model layer
    boolean validatePassword(String password);

    boolean isAuthDataValid(String password, String email);
}
