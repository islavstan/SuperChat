package com.internship.supercoders.superchat.registration;



public interface RegistrationView {

    void showProgress();

    void hideProgress();

    void setEmailError();

    void setPasswordError();

    void hidePasswordError();

    void setBlankFields();

    void navigateToLogin(String token);

    void hideEmailError();

    void registration();

    void registrationError();

    void  openImageChooser();

}


