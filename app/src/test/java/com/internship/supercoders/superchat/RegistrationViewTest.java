package com.internship.supercoders.superchat;


import com.internship.supercoders.superchat.registration.RegistrInteractorImpl;
import com.internship.supercoders.superchat.registration.RegistrationActivity;
import com.internship.supercoders.superchat.registration.RegistrationPresenter;
import com.internship.supercoders.superchat.registration.RegistrationView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationViewTest {

    @Mock
    RegistrationView view;

    @Mock
    RegistrationPresenter presenter;

    @InjectMocks
    RegistrationActivity activity;
    @Mock
    RegistrInteractorImpl interacor;




    @Test
    public void isValidEmailAndPassword() throws Exception {
        String email = "test@test.test";
        String password = "pas";
        String password2 = "pas";
        File file =new File("path");
        /*when(view.isValidData(email,password,password2)).thenReturn(true);
        activity.registration(file,email,email,email,email,email,email);
        verify(presenter).validateData(file,email,email,email,email,email);*/



    }
}
