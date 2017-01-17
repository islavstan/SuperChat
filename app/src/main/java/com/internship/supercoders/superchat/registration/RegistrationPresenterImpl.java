package com.internship.supercoders.superchat.registration;


public class RegistrationPresenterImpl implements RegistrationPresenter,RegistrationInteractor.RegistrationFinishedListener{

   private RegistrationView registrationView;
    private RegistrationInteractor regInteractor;

    public RegistrationPresenterImpl(RegistrationView registrationView) {
        this.registrationView = registrationView;
        regInteractor = new RegistrInteractorImpl();
    }

    @Override
    public void onError() {
        if (registrationView != null) {
            registrationView.registrationError();
        }
    }

    @Override
    public void onSuccess(String token) {
        if (registrationView != null) {
            registrationView.hideProgress();
            registrationView.navigateToLogin(token);
        }
    }

    @Override
    public void validateData(String email, String password, String fullname, String phone, String website) {
        if (registrationView != null) {
            registrationView.showProgress();
        }

        regInteractor.authorization(email, password, fullname, phone, website, this);
    }


 @Override
    public void onDestroy() {

    }
}