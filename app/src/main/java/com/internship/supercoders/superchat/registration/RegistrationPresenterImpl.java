package com.internship.supercoders.superchat.registration;


import android.content.Context;

import java.io.File;

public class RegistrationPresenterImpl implements RegistrationPresenter,RegistrationInteractor.RegistrationFinishedListener{

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
    public void validateData( File file,String email, String password, String fullname, String phone, String website) {
        if (registrationView != null) {
            registrationView.hideEmailError();
            registrationView.hidePasswordError();
            registrationView.showProgress();
        }

        regInteractor.authorization(file,email, password, fullname, phone, website, this);
    }






    @Override
    public void onDestroy() {

    }

    @Override
    public Context getContext() {
       return  registrationView.getContext();
    }
}
