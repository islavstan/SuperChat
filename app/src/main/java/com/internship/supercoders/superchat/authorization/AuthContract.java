package com.internship.supercoders.superchat.authorization;

import android.content.Context;

import com.internship.supercoders.superchat.models.user_authorization_response.LogAndPas;

/**
 * Created by RON on 21.01.2017.
 */
public interface AuthContract {

    interface View {

        void showProgress();

        void hideProgress();

        void setEmailError();

        void hideEmailError();

        void setPasswordError();

        void hidePasswordError();

        void setBlankFields();

        void authorization();

        void authorizationError();

        void keepMeSignedIn();

        void openRecoveryPasswordDialog();

        void onBtnSignIn();

        void onBtnSignUp();

        void writeUserAuthDataToDB(LogAndPas logAndPas);

        void navigateToLogin(String token);

        boolean isEmailValid(String email);

        boolean isPasswordValid(String password);

        Context getContext();
    }

    interface Presenter {

        void onError();

        void onSuccess(String token);

        void onDestroy();

        void validateData(LogAndPas logAndPas);
    }

}
