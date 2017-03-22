package com.internship.supercoders.superchat.registration;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.internship.supercoders.superchat.db.DBMethods;

import java.io.File;

public interface RegistrationInteractor {


    interface RegistrationFinishedListener {

        void onError();

        void onErrorWithToken(String token, String error);

        void onSuccess(String token);

        void onSuccessFacebookLogin(String id);

        void hideError(int item);

        void showPasswordLengthError(int layout);

        void showPasswordError(int layout);

        void showEmailError();

        void uploadPhotoError();

        void enableSignUp();

        void disableSignUp();

        void setPhotoFromCamera(File photoFile, Bitmap photoBitmap);

        void showConfirmPasswordError();

    }


    void facebookLogin(LoginButton logBtn, CallbackManager callbackManager, RegistrationFinishedListener listener);

    void registration(DBMethods dbMethods, File file, String token, String email, String password, String fullname, String phone, String website, String facebookId, RegistrationFinishedListener listener);

    void authorization(DBMethods dbMethods, String token, File file, String email, String password, String fullname, String phone, String website, String facebookId, RegistrationFinishedListener listener);

    void createFile(DBMethods db, String userId, File file, String token, RegistrationFinishedListener listener);

    void userAuthorization(String email, String password, RegistrationFinishedListener listener);

    void signIn(DBMethods db, String userId, File file, String token, String email, String password, RegistrationFinishedListener listener);

    void uploadFile(DBMethods db, String userId, String image_id, String token, RegistrationFinishedListener listener, String contentType, String expires, String acl, String key, String policy, String success_action_status, String x_amz_algorithm
            , String x_amz_credential, String x_amz_date, String x_amz_signature, File file);

    void declaringFileUploaded(DBMethods db, String userId, String image_id, String token, File file, RegistrationFinishedListener listener);

    void validateUserInfo(EditText emailET, EditText passwordET, EditText confPassET, Button signupBtn, RegistrationFinishedListener listener);

    boolean validatePassword(String password);

    boolean validateEmail(String email);

    void unsubscribe();

    void makePhotoFromCamera(Intent data, File dir, RegistrationFinishedListener listener);

    void updateUserAva(DBMethods db, File file, String image_id, String userId, String token, RegistrationFinishedListener listener);

    void destroySession(String token);
}


