package com.internship.supercoders.superchat.splashScreen;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.internship.supercoders.superchat.MainActivity;
import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.registration.RegistrationActivity;

public class SplashScreenActivity extends AppCompatActivity implements SplashScreenView {

    private static final String TAG = "SplashScreenActivity";
    private SplashScreenPresenterImpl presenter;

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
    public void navigateToMainScreen(String token) {
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    @Override
    public void navigateToAuthorScreen() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }
}
