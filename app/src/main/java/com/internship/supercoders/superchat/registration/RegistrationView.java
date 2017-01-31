package com.internship.supercoders.superchat.registration;


import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;

public interface RegistrationView {

    void showProgress();

    void hideProgress();

    void navigateToLogin(String token);

    void registration(String token, File photo, String email, String password, String conf_password, String fullname, String phone, String website, String facebookId);

    void showRegistrationError();

    void openImageChooser();

    void changeFacebookBtnText(String facebookId);

    void showRegistrationErrorWithToken(String token, String error);

    Context getContext();


    void hideError(int layout);

    void showConfirmPasswordError();

    void showPasswordLengthError(int layout);

    void showPasswordError(int layout);

    void showEmailError();

    void enableSignUp();

    void disableSignUp();

    void showDialogForCameraOrGallery();

    void openCamera();

    void setPhotoFromCamera(File photoFile, Bitmap photoBitmap);

    void showInternetConnectionError();

}



