package com.internship.supercoders.superchat.authorization;

import android.content.Context;

/**
 * Created by RON on 05.02.2017.
 */
public interface AuthView {


    void showChangePasswordDialog();

    void showProgress();

    void hideProgress();

    void setEmailIsEmptyError();

    void setEmailIsInvalidError();

    void hideEmailError();

    void setPasswordIsEmptyError();

    void hidePasswordError();

    void setBlankFields();

    // TODO: 1/30/17 [Code Review] It is not obvious what this method really does, please rename
    void signInUser();

    // TODO: 1/30/17 [Code Review] It is not obvious what this method really does, please rename
    void showAuthorizationError(String error);

    // TODO: 1/30/17 [Code Review] It is not obvious what this method really does, please rename
    void setUserSignedIn();

    void openRecoveryPasswordDialog();

    void onBtnSignIn();

    void onBtnSignUp();

    void navigateToLogin(String token);

    Context getContext();
}
