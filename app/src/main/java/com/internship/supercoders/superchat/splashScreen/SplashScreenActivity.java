package com.internship.supercoders.superchat.splashScreen;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.internship.supercoders.superchat.MainActivity;
import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.authorization.AuthorizationActivity;
import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.utils.UserPreferences;

public class SplashScreenActivity extends AppCompatActivity implements SplashScreenView {

    private static final String TAG = "SplashScreenActivity";
    private SplashScreenPresenterImpl presenter;
    private RelativeLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        TextView tvLogoLabel = (TextView) findViewById(R.id.tv_logo_label);
        Typeface font = Typeface.createFromAsset(getAssets(), "font/FortuneCity.ttf");
        tvLogoLabel.setTypeface(font);
        presenter = new SplashScreenPresenterImpl(this, new DBMethods(this), new UserPreferences(this));

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
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Splash Activity", "Call OnDestroy()");
        presenter.unsubscribe();
    }
}
