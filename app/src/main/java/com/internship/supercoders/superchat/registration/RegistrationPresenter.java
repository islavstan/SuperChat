package com.internship.supercoders.superchat.registration;


import android.content.Context;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

import java.io.File;

public interface RegistrationPresenter {
    void validateData( File file, String email, String password, String fullname, String phone, String website,String facebookId);
    void facebookLogin(LoginButton logBtn, CallbackManager callbackManager);
    void onDestroy();

}