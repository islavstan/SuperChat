package com.internship.supercoders.superchat.registration;


import android.content.Context;

public interface RegistrationView {

    void showProgress();

    void hideProgress();

    void setEmailError();

    void setPasswordError();

    void hidePasswordError();

    void setBlankFields();

    void navigateToLogin(String token);

    void hideEmailError();

    void registration();

    void registrationError();

    void  openImageChooser();

Context getContext();

}


