package com.internship.supercoders.superchat;


import com.internship.supercoders.superchat.registration.RegistrationPresenter;
import com.internship.supercoders.superchat.registration.RegistrationPresenterImpl;
import com.internship.supercoders.superchat.registration.RegistrationView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationViewTest {
    RegistrationPresenter presenter;
    @Mock
    RegistrationView view;


    @Before
    public void setUp() throws Exception {
        presenter = new RegistrationPresenterImpl(view);


    }

    @Test
    public void isValidEmailAndPassword() throws Exception {
        String email = "test@test.test";
        String password = "pas";
        String password2 = "pas";
      when(view.isValidData(email,password,password2)).thenReturn(true);
        verify(view).hideEmailError();



    }
}
