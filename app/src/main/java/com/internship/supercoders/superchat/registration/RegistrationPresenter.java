package com.internship.supercoders.superchat.registration;


public interface RegistrationPresenter {
    void validateData(String email,String password, String fullname,String phone,String website);
    void onDestroy();
}