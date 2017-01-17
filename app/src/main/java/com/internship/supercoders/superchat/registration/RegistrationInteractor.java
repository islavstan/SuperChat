package com.internship.supercoders.superchat.registration;



public interface RegistrationInteractor {

    interface RegistrationFinishedListener {

        void onError();

        void onSuccess(String token);

    }

    void registration(String token,String email, String password, String fullname,String phone, String website, RegistrationFinishedListener listener);

    void authorization (String email, String password, String fullname,String phone, String website, RegistrationFinishedListener listener);


}