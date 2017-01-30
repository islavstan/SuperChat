package com.internship.supercoders.superchat.authorization;

import android.content.Context;

import com.internship.supercoders.superchat.models.user_authorization_response.LogAndPas;

/**
 * Created by RON on 21.01.2017.
 */
public interface AuthContract {

    interface View {


        void showChangePasswordDialog();

        void showProgress();

        void hideProgress();

        void setEmailError();

        void hideEmailError();

        void setPasswordError();

        void hidePasswordError();

        void setBlankFields();

        // TODO: 1/30/17 [Code Review] It is not obvious what this method really does, please rename
        void authorization();

        // TODO: 1/30/17 [Code Review] It is not obvious what this method really does, please rename
        void authorizationError();

        // TODO: 1/30/17 [Code Review] It is not obvious what this method really does, please rename
        void keepMeSignedIn();

        void openRecoveryPasswordDialog();

        void onBtnSignIn();

        void onBtnSignUp();

        // TODO: 1/30/17 [Code Review] This is a part of business logic, move it to interactors/model layer
        void writeUserAuthDataToDB(LogAndPas logAndPas);

        void navigateToLogin(String token);

        // TODO: 1/30/17 [Code Review] This is a part of business logic, move it to interactors/model layer
        boolean isEmailValid(String email);

        // TODO: 1/30/17 [Code Review] This is a part of business logic, move it to interactors/model layer
        boolean isPasswordValid(String password);

        Context getContext();
    }

    interface Presenter {

        void onError();

        void onSuccess(String token);

        void onDestroy();
        void changePassword();

        void validateData(LogAndPas logAndPas);
    }

}
