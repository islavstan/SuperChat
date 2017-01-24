package com.internship.supercoders.superchat;


import android.support.design.widget.TextInputLayout;

import com.internship.supercoders.superchat.registration.RegistrInteractorImpl;
import com.internship.supercoders.superchat.registration.RegistrationActivity;
import com.internship.supercoders.superchat.registration.RegistrationPresenter;
import com.internship.supercoders.superchat.registration.RegistrationPresenterImpl;
import com.internship.supercoders.superchat.registration.RegistrationView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
      when(view.isValidData(email,password,password2)).thenReturn(true);
        activity.registration(file,email,email,email,email,email,email);
        verify(presenter).validateData(file,email,email,email,email,email);



    }
}
