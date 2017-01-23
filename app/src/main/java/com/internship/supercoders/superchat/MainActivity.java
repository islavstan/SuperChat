package com.internship.supercoders.superchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.internship.supercoders.superchat.splashScreen.SplashScreenActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent message = getIntent();
        TextView tvTest = (TextView) findViewById(R.id.tvTest);
        tvTest.setText("User token/n" + message.getStringExtra("token"));
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
