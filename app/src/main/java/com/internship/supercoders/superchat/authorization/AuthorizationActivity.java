package com.internship.supercoders.superchat.authorization;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.models.user_authorization_response.LogAndPas;
import com.internship.supercoders.superchat.registration.RegistrationActivity;

public class AuthorizationActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnSignUp;
    private Button btnSignIn;

    private EditText etEmail;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        btnSignUp = (Button) findViewById(R.id.signup_btn);
        btnSignUp.setOnClickListener(this);

        btnSignIn = (Button) findViewById(R.id.btn_sign_in);
        btnSignIn.setOnClickListener(this);

        etEmail = (EditText) findViewById(R.id.input_email);
        etPassword = (EditText) findViewById(R.id.input_password);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.signup_btn:
                intent = new Intent(this, RegistrationActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_sign_in:
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                DBMethods db = new DBMethods(this);

                db.readFromDB();
                db.writeAuthData(new LogAndPas(email, password));
                break;
        }
    }
}
