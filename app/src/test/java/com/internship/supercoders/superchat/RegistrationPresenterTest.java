package com.internship.supercoders.superchat;

import com.internship.supercoders.superchat.registration.RegistrInteractorImpl;
import com.internship.supercoders.superchat.registration.RegistrationPresenterImpl;
import com.internship.supercoders.superchat.registration.RegistrationView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationPresenterTest {
    @Mock
    RegistrationView view;
    @Mock
    RegistrInteractorImpl interacor;

    @InjectMocks
    RegistrationPresenterImpl registrationPresenter;

    @Test
    public void errorTest() throws Exception {
        registrationPresenter.onError();
        verify(view).showRegistrationError();

    }

    @Test
    public void successTest() throws Exception {
        String token ="token";
        registrationPresenter.onSuccess(token);
        verify(view).hideProgress();
        verify(view).navigateToLogin(token);


    }


    @Test
    public void validateDataTest() throws Exception {
        File file =new File("path");
        String data = "data";
        registrationPresenter.validateData(file,data,data,data,data,data);
        verify(view).showProgress();
        verify(view).hidePasswordError();
        verify(view).hideEmailError();
        verify(interacor).authorization(file,data,data,data,data,data,registrationPresenter);

    }
}
