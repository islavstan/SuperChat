package com.internship.supercoders.superchat.authorization;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.crashlytics.android.Crashlytics;
import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.models.user_authorization_response.LogAndPas;
import com.internship.supercoders.superchat.registration.RegistrationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

public class AuthorizationActivity extends AppCompatActivity implements AuthContract.View {

    @BindView(R.id.btn_sign_in)
    Button  btnSignIn;
    @BindView(R.id.signup_btn)
    Button btnSignUp;

    @BindView(R.id.input_email)
    EditText etEmail;
    @BindView(R.id.input_password)
    EditText etPassword;

    @BindView(R.id.input_layout_email)
    TextInputLayout ilEmail;
    @BindView(R.id.input_layout_password)
    TextInputLayout ilPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_authorization);
        ButterKnife.bind(this);
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setEmailError() {

    }

    @Override
    public void hideEmailError() {

    }

    @Override
    public void setPasswordError() {

    }

    @Override
    public void hidePasswordError() {

    }

    @Override
    public void setBlankFields() {

    }

    @Override
    public void authorization() {

    }

    @Override
    public void authorizationError() {

    }

    @Override
    public void keepMeSignedIn() {

    }

    @Override
    public void openRecoveryPasswordDialog() {

    }

    @OnClick(R.id.btn_sign_in)
    @Override
    public void onBtnSignIp() {
        writeUserDataToDB();
    }

    @OnClick(R.id.signup_btn)
    @Override
    public void onBtnSignUp() {
        Intent intent;
        intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    @Override
    public void writeUserDataToDB() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        DBMethods db = new DBMethods(this);

        db.readFromDB();
        db.writeAuthData(new LogAndPas(email, password));
    }

    @Override
    public void navigateToLogin(String token) {

    }

    @Override
    public boolean isValidData(String email, String password, String confirm_password) {
        return false;
    }

    @Override
    public Context getContext() {
        return this;
    }
}
