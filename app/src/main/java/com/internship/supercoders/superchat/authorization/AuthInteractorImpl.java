package com.internship.supercoders.superchat.authorization;

import android.text.TextUtils;

import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.models.user_authorization_response.VerificationData;

/**
 * Created by RON on 05.02.2017.
 */
public class AuthInteractorImpl implements AuthInteractor {

    AuthView authView;

    AuthInteractorImpl(AuthorizationActivity authorizationActivity) {
        this.authView = authorizationActivity;
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
        }else {
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
        if (TextUtils.isEmpty(password)){
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


}
