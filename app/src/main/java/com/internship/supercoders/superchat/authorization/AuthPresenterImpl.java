package com.internship.supercoders.superchat.authorization;

import android.widget.EditText;

import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.models.user_authorization_response.VerificationData;
import com.internship.supercoders.superchat.utils.UserPreferences;


public class AuthPresenterImpl implements AuthPresenter, AuthInteractor.AuthFinishedListener {
    AuthView view;
    UserPreferences userPreferences;
    AuthInteractor interactor;

    public AuthPresenterImpl(AuthView view) {
        this.view = view;
        // TODO: 1/30/17 [Code Review] You should use this instance in AuthPresenter layer
        userPreferences = new UserPreferences(view.getContext());
        interactor = new AuthInteractorImpl();
    }

    @Override
    public void onError() {
        if (view != null) {
            //  view.showAuthorizationError();
        }
    }


    @Override
    public void onDestroy() {
        // TODO: 1/30/17 [Code Review] Nullify view instance here!
        view = null;
    }

    @Override
    public void changePassword() {

    }

    @Override
    public void validateData(VerificationData verificationData) {
        view.showProgress();
    }

    @Override
    public void signIn(DBMethods dbMethods, String token, String login, String password) {
        interactor.userAthorization(dbMethods, password, login, this);
    }

    @Override
    public void validateUserInfo(EditText email, EditText password) {
      interactor.validateUserInfo(email, password, this);
    }

    @Override
    public void unsubscribe() {
        interactor.unsubscribe();
    }


    @Override
    public void onSuccess(String token) {
        view.navigateToLogin(token);

    }

    @Override
    public void hideError(int item) {
        view.hideError(item);

    }

    @Override
    public void onError(String error) {
        view.showAuthorizationError(error);
    }

    @Override
    public void enableLogin() {
     view.enableLogin();
    }

    @Override
    public void disableLogin() {
    view.disableLogin();
    }

    @Override
    public void showEmailError() {
       view.showEmailError();
    }

    @Override
    public void showPasswordLengthError() {
         view.showPasswordLengthError();
    }

    @Override
    public void showPasswordError() {
     view.showPasswordError();
    }
}
