package com.internship.supercoders.superchat.registration;


import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.internship.supercoders.superchat.db.DBMethods;

import java.io.File;

public interface RegistrationPresenter {
    void validateData(DBMethods dbMethods, String token, File file, String email, String password, String fullname, String phone, String website, String facebookId);

    void facebookLogin(LoginButton logBtn, CallbackManager callbackManager);

    void validateUserInfo(EditText emailET, EditText passwordET, EditText confPassET, Button signupBtn);

    void unsubscribe();

    void onDestroy();

    void makePhotoFromCamera(Intent data, File cacheDir);

    void destroySession(String token);


}