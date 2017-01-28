package com.internship.supercoders.superchat.registration;


import android.content.Context;

import java.io.File;

public interface RegistrationView {

    void showProgress();

    void hideProgress();

    void navigateToLogin(String token);

    void registration(File photo, String email, String password, String conf_password, String fullname, String phone, String website, String facebookId);

    void registrationError();

    void openImageChooser();

    void changeFacebookBtnText(String facebookId);


    Context getContext();


    void hideError(int layout);

    void showConfirmPasswordError();

    void showPasswordLengthError(int layout);

    void showPasswordError(int layout);

    void showEmailError();

    boolean validatePassword(String password);

    boolean validateEmail(String email);

    void enableSignUp();

    void disableSignUp();

    void dialogForCameraOrGallery();

    void cameraIntent();

}


