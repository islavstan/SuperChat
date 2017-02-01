package com.internship.supercoders.superchat;

import com.internship.supercoders.superchat.models.registration_request.ReqUser;
import com.internship.supercoders.superchat.registration.RegistrInteractorImpl;
import com.internship.supercoders.superchat.registration.RegistrationInteractor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith( MockitoJUnitRunner.class )
public class RegistrationInteractorTest {

 /*   @Mock
    private Call<Object> mockObjectCall;
    @Mock
    RegistrationPoint regPoint;
    @Mock
    ReqUser user;
    @Mock
    RegistrationInteractor.RegistrationFinishedListener listener;
    @InjectMocks
    RegistrInteractorImpl regInterImpl;
    @Captor
    private ArgumentCaptor<Callback<Object>> objCallBack;


    @Test
    public void registrationTest() throws Exception {
        Object obj = new Object();
        String data = "data";
        File file = new File("path");
       // RegistrationInteractor.RegistrationFinishedListener listener = mock(RegistrationInteractor.RegistrationFinishedListener.class);
        Response<Object> response = Response.success(obj);
        when(regPoint.registration(data, data, data, user)).thenReturn(mockObjectCall);
        regInterImpl.registration(file, data, data, data, data, data, data, listener);
        Mockito.verify(regPoint).registration(data, data, data, user);
        Mockito.verify(mockObjectCall).enqueue(objCallBack.capture());
        objCallBack.getValue().onResponse(mockObjectCall, response);
        Mockito.verify(listener).onSuccess(data);


    }*/


    @Test
    public void authorizationTest() throws Exception {


    }
}
