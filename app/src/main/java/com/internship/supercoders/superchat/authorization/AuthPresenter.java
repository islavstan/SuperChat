package com.internship.supercoders.superchat.authorization;

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

    }

    @Override
    public void validateData(LogAndPas logAndPas) {
        view.showProgress();
    }
}
