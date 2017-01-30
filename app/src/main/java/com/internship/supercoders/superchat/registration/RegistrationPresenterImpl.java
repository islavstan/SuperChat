package com.internship.supercoders.superchat.registration;


import android.content.Context;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

import java.io.File;

public class RegistrationPresenterImpl implements RegistrationPresenter,RegistrationInteractor.RegistrationFinishedListener{

    // TODO: 1/30/17 [Code Review] context is redundant
    Context context;
   private RegistrationView registrationView;
    private RegistrationInteractor regInteractor;

    public RegistrationPresenterImpl(RegistrationView registrationView,RegistrationInteractor regInteractor) {
        this.registrationView = registrationView;
        this.regInteractor = regInteractor;
    }


    @Override
    public void onError() {
        if (registrationView != null) {
            registrationView.registrationError();
            registrationView.hideProgress();
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
    public void validateData( File file,String email, String password, String fullname, String phone, String website,String facebookId) {
        if (registrationView != null) {
            registrationView.showProgress();
        }

        regInteractor.authorization(file,email, password, fullname, phone, website,facebookId, this);
    }

    @Override
    public void facebookLogin(LoginButton logBtn, CallbackManager callbackManager) {
        regInteractor.facebookLogin(logBtn,callbackManager,this);
    }


    @Override
    public void onDestroy() {
        // TODO: 1/30/17 [Code Review] nullify view reference here
    }

    @Override
    public Context getContext() {
       return  registrationView.getContext();
    }
}
