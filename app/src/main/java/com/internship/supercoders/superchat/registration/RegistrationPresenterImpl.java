package com.internship.supercoders.superchat.registration;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

import java.io.File;

public class RegistrationPresenterImpl implements RegistrationPresenter,RegistrationInteractor.RegistrationFinishedListener {


    private RegistrationView registrationView;
    private RegistrationInteractor regInteractor;

    public RegistrationPresenterImpl(RegistrationView registrationView, RegistrationInteractor regInteractor) {
        this.registrationView = registrationView;
        this.regInteractor = regInteractor;
    }


    @Override
    public void onError() {
        if (registrationView != null) {
            registrationView.showRegistrationError();
            registrationView.hideProgress();
            registrationView.enableSignUp();
        }
    }

    @Override
    public void onErrorWithToken(String token, String error) {
        if (registrationView != null) {
            registrationView.hideProgress();
            registrationView.showRegistrationErrorWithToken(token, error);
            registrationView.enableSignUp();
        }
    }

    @Override
    public void onSuccess(String token) {
        if (registrationView != null) {
            registrationView.hideProgress();
            registrationView.navigateToLogin(token);
        }
    }

    @Override
    public void onSuccessFacebookLogin(String id) {
        registrationView.changeFacebookBtnText(id);
    }

    @Override
    public void validateData(String token, File file, String email, String password, String fullname, String phone, String website, String facebookId) {
        if (registrationView != null) {
            registrationView.showProgress();
            registrationView.disableSignUp();
        }

        regInteractor.authorization(token, file, email, password, fullname, phone, website, facebookId, this);
    }

    @Override
    public void facebookLogin(LoginButton logBtn, CallbackManager callbackManager) {
        regInteractor.facebookLogin(logBtn, callbackManager, this);
    }

    @Override
    public void validateUserInfo(EditText emailET, EditText passwordET, EditText confPassET, Button signupBtn) {
        regInteractor.validateUserInfo(emailET, passwordET, confPassET, signupBtn, this);
    }

    @Override
    public void unsubscribe() {
        regInteractor.unsubscribe();
    }

    @Override
    public void hideError(int item) {
        if (registrationView != null) {
            registrationView.hideError(item);
        }
    }

    @Override
    public void showPasswordLengthError(int layout) {
        if (registrationView != null) {
            registrationView.showPasswordLengthError(layout);
        }
    }

    @Override
    public void showPasswordError(int layout) {
        if (registrationView != null) {
            registrationView.showPasswordError(layout);
        }
    }

    @Override
    public void showEmailError() {
        if (registrationView != null) {
            registrationView.showEmailError();
        }
    }

    @Override
    public void enableSignUp() {
        if (registrationView != null) {
            registrationView.enableSignUp();
        }
    }

    @Override
    public void disableSignUp() {
        if (registrationView != null) {
            registrationView.disableSignUp();
        }
    }


    @Override
    public void showConfirmPasswordError() {
        if (registrationView != null) {
            registrationView.showConfirmPasswordError();
        }
    }


    @Override
    public void onDestroy() {
      registrationView = null;

        // TODO: 1/30/17 [Code Review] nullify view reference here
    }

    @Override
    public void makePhotoFromCamera(Intent data, File cacheDir) {
        regInteractor.makePhotoFromCamera(data, cacheDir, this);
    }


    @Override
    public void setPhotoFromCamera(File photoFile, Bitmap photoBitmap) {
        if (registrationView != null) {
            registrationView.setPhotoFromCamera(photoFile, photoBitmap);

        }
    }


}
