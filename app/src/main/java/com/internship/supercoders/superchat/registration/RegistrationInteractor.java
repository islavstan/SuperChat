package com.internship.supercoders.superchat.registration;


import java.io.File;

public interface RegistrationInteractor {

    interface RegistrationFinishedListener {

        void onError();

        void onSuccess(String token);

    }

    void registration(boolean image, String token, String email, String password, String fullname, String phone, String website, RegistrationFinishedListener listener);

    void authorization(boolean image, String email, String password, String fullname, String phone, String website, RegistrationFinishedListener listener);

    void createFile(String token, RegistrationFinishedListener listener);

    void userAuthorization(String email, String password, RegistrationFinishedListener listener);

    void signIn(String token, String email, String password, RegistrationFinishedListener listener);

    void uploadFile(String contentType, String expires, String acl, String key, String policy, String success_action_status, String x_amz_algorithm
            , String x_amz_credential, String x_amz_date, String x_amz_signature, File file);

}