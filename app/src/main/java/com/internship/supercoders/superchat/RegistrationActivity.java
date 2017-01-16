package com.internship.supercoders.superchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegistrationActivity extends AppCompatActivity {
   private EditText emailET, passwordET, conf_passwET, fullnameET, phoneET, websiteET;
   private Button facebookBtn, signupBtn;
   private CircleImageView userPhoto;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");

        emailET=(EditText)findViewById(R.id.input_email);
        passwordET=(EditText)findViewById(R.id.input_password);
        conf_passwET=(EditText)findViewById(R.id.input_confirm_password);
        fullnameET=(EditText)findViewById(R.id.input_fullname);
        phoneET=(EditText)findViewById(R.id.input_phone);
        websiteET=(EditText)findViewById(R.id.input_website);
        userPhoto =(CircleImageView)findViewById(R.id.photo);
        signupBtn =(Button)findViewById(R.id.signup_btn);

    }
}
