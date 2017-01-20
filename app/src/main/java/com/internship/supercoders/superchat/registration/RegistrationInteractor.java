package com.internship.supercoders.superchat.registration;


import android.content.Context;

import java.io.File;

public interface RegistrationInteractor {

    interface RegistrationFinishedListener {

        void onError();

        void onSuccess(String token);

        Context getContext();

    }



    void registration(File file, String token, String email, String password, String fullname, String phone, String website, RegistrationFinishedListener listener);

    void authorization(File file, String email, String password, String fullname, String phone, String website, RegistrationFinishedListener listener);

    void createFile(File file,String token, RegistrationFinishedListener listener);

    void userAuthorization(String email, String password, RegistrationFinishedListener listener);

    void signIn(File file,String token, String email, String password, RegistrationFinishedListener listener);

    void uploadFile( String image_id,String token,RegistrationFinishedListener listener,String contentType, String expires, String acl, String key, String policy, String success_action_status, String x_amz_algorithm
            , String x_amz_credential, String x_amz_date, String x_amz_signature, File file);

    void declaringFileUploaded(String image_id, String token,File file,RegistrationFinishedListener listener);
}