package com.internship.supercoders.superchat.splash_screen;

import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.models.user_authorization_response.VerificationData;
import com.internship.supercoders.superchat.utils.UserPreferences;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Max on 02.02.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class SplashScreePresenterTest {
    @Mock
    SplashScreenView view;
    @Mock
    SplashScreenInteractor interactor;
    @Mock
    UserPreferences userPreferences;
    @Mock
    DBMethods dbManager;

    @InjectMocks
    SplashScreenPresenterImpl presenter;

    @Test
    public void navigateToMainScreenShouldBeCalledIfUserAlreadyLoggedIn() throws InterruptedException {
        VerificationData user = new VerificationData("max@g.com", "superpass");
        when(userPreferences.isUserSignedIn()).thenReturn(true);
        when(dbManager.getAuthData()).thenReturn(user);
        presenter.sleep(3000);
        Thread.sleep(3000);
        verify(view, times(1)).navigateToMainScreen();
    }
}
