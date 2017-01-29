package com.internship.supercoders.superchat.splashScreen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.internship.supercoders.superchat.MainActivity;
import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.authorization.AuthorizationActivity;
import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.models.user_authorization_response.LogAndPas;
import com.internship.supercoders.superchat.utils.UserPreferences;

public class SplashScreenActivity extends AppCompatActivity implements SplashScreenView {

    private static final String TAG = "SplashScreenActivity";
    private SplashScreenPresenterImpl presenter;
    private UserPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        TextView tvLogoLabel = (TextView) findViewById(R.id.tv_logo_label);
        Typeface font = Typeface.createFromAsset(getAssets(), "font/FortuneCity.ttf");
        tvLogoLabel.setTypeface(font);
        presenter = new SplashScreenPresenterImpl(this);
        presenter.sleep(3000);

    }

    @Override
    public void navigateToMainScreen(@Nullable String token) {
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    @Override
    public void navigateToAuthorScreen(@Nullable String token) {
        Intent intent = new Intent(SplashScreenActivity.this, AuthorizationActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    @Override
    public boolean isAuth() {
        preferences = new UserPreferences(this);
        return preferences.isUserSignedIn();
    }

    @Override
    public LogAndPas getLogAndPas() {
        DBMethods db = new DBMethods(this);
        return db.getAuthData();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
        preferences = null;
    }
}
