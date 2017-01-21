package com.internship.supercoders.superchat.authorization;

import android.content.Context;

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

        void onBtnSignIp();

        void onBtnSignUp();

        void writeUserDataToDB();

        void navigateToLogin(String token);

        boolean isValidData(String email, String password, String confirm_password);

        Context getContext();
    }

    interface Presenter {

        void onDestroy();

        void validateData(String email, String password);
    }

}
