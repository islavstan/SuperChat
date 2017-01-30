package com.internship.supercoders.superchat.authorization;

import android.app.AlertDialog;

import com.internship.supercoders.superchat.models.user_authorization_response.LogAndPas;

/**
 * Created by RON on 21.01.2017.
 */
public class AuthPresenter implements AuthContract.Presenter {
    AuthContract.View view;

    public AuthPresenter(AuthContract.View view){
        this.view = view;
    }

    @Override
    public void onError() {
        if (view != null) {
            view.authorizationError();
        }
    }

    @Override
    public void onSuccess(String token) {
        if (view != null) {
            view.hideProgress();
            view.navigateToLogin(token);
        }
    }

    @Override
    public void onDestroy() {
        // TODO: 1/30/17 [Code Review] Nullify view instance here!
    }

    @Override
    public void changePassword() {

    }

    @Override
    public void validateData(LogAndPas logAndPas) {
        view.showProgress();
    }
}
