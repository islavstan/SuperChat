package com.internship.supercoders.superchat.registration;


import android.content.Context;

import java.io.File;

public interface RegistrationView {

    void showProgress();

    void hideProgress();

    void setEmailError();

    void setPasswordError();

    void hidePasswordError();

    void setBlankFields();

    void navigateToLogin(String token);

    void hideEmailError();

    void registration(File photo, String email, String password, String conf_password, String fullname, String phone, String website);

    void registrationError();

    void  openImageChooser();

    boolean isValidData(String email, String password, String confirm_password);

Context getContext();

}


