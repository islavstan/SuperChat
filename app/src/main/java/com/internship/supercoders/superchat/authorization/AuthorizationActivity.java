package com.internship.supercoders.superchat.authorization;

import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.internship.supercoders.superchat.MainActivity;
import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.data.AppConsts;
import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.forgot_password.ForgotPasswordDialog;
import com.internship.supercoders.superchat.models.user_authorization_response.VerificationData;
import com.internship.supercoders.superchat.registration.RegistrationActivity;
import com.internship.supercoders.superchat.utils.UserPreferences;
import com.internship.supercoders.superchat.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthorizationActivity extends AppCompatActivity implements AuthContract.View {

    @BindView(R.id.activity_main)
    RelativeLayout relativeLayout;

    @BindView(R.id.btn_sign_in)
    Button  btnSignIn;
    @BindView(R.id.signup_btn)
    Button btnSignUp;

    @BindView(R.id.forgot_password)
    TextView tvForgotPassword;

    @BindView(R.id.input_email)
    EditText etEmail;
    @BindView(R.id.input_password)
    EditText etPassword;

    @BindView(R.id.input_layout_email)
    TextInputLayout ilEmail;
    @BindView(R.id.input_layout_password)
    TextInputLayout ilPassword;

    @BindView(R.id.progressbar)
    ProgressBar pb;

    @BindView(R.id.cb_keep_me_signed_in)
    CheckBox checkBox;

    AuthPresenter authPresenter;
    ViewUtils viewUtils;
    UserPreferences userPreferences;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        ButterKnife.bind(this);
        Intent intent = getIntent();
         token = intent.getStringExtra("token");
        Log.i(AppConsts.LOG_TAG, "Session token " + token);

        viewUtils = new ViewUtils(this);
        authPresenter = new AuthPresenter(this);

        // TODO: 1/30/17 [Code Review] You should use this instance in Presenter layer
        userPreferences = new UserPreferences(this);

//        viewUtils.hideKeyboard();

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isEmailValid(s.toString());
            }
        });

        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    isEmailValid(((EditText) v).getText().toString());
                }
            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isPasswordValid(s.toString());
            }
        });

        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    isPasswordValid(((EditText) v).getText().toString());
                }
            }
        });
    }


    @Override
    public void showProgress() {
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pb.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setEmailError() {
        ilEmail.setError(getString(R.string.invalid_email));
    }

    @Override
    public void hideEmailError() {
        ilEmail.setError("");
    }

    @Override
    public void setPasswordError() {
        ilPassword.setError(getString(R.string.invalid_password));
    }

    @Override
    public void hidePasswordError() {
        ilPassword.setError("");
    }

    @Override
    public void setBlankFields() {
        viewUtils.showShackLong(R.string.fill_fields);
    }

    @Override
    public void authorization() {

    }

    @Override
    public void authorizationError() {

    }

    @Override
    public void keepMeSignedIn() {
        userPreferences.kepUserSignedIn(checkBox.isChecked());
    }

    @OnClick(R.id.forgot_password)
    @Override
    public void openRecoveryPasswordDialog() {
        Log.d(AppConsts.LOG_TAG, "openRecoveryPasswordDialog");
    }





    @OnClick(R.id.forgot_password)
    @Override
    public void showChangePasswordDialog() {
        FragmentManager fm = this.getFragmentManager();
        ForgotPasswordDialog forgotPasswordDialog =new ForgotPasswordDialog();
        forgotPasswordDialog.show(fm,"dialog");


    }

    @OnClick(R.id.btn_sign_in)
    @Override
    public void onBtnSignIn() {
        // TODO: 1/30/17 [Code Review] This is a part of business logic, move it to interactors/model layer
        // all the validation should not be here
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        VerificationData verificationData = new VerificationData(email, password);

        boolean isPasswordValid = isPasswordValid(password);
        boolean isEmailValid = isEmailValid(email);
        boolean isValid = isEmailValid && isPasswordValid;

        if(isValid){
            authPresenter.validateData(verificationData);
            hidePasswordError();
            hideEmailError();
            writeUserAuthDataToDB(verificationData);
            keepMeSignedIn();
            Log.i(AppConsts.LOG_TAG, "Check Sign IN: " + Boolean.toString(userPreferences.isUserSignedIn()));
        }
    }

    @OnClick(R.id.signup_btn)
    @Override
    public void onBtnSignUp() {
        Intent intent;
        intent = new Intent(this, RegistrationActivity.class);
        intent.putExtra("token",token);
        startActivity(intent);
    }

    @Override
    public void writeUserAuthDataToDB(VerificationData verificationData) {
        // TODO: 1/30/17 [Code Review] NNNNNOOOOOOOOOOOOOO!!!!!!!!!!!!!!!!!!!
        DBMethods db = new DBMethods(this);

        db.readFromDB();
        db.writeAuthData(verificationData);
    }

    @Override
    public void navigateToLogin(String token) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("token",token);
        startActivity(intent);
    }

    @Override
    public boolean isEmailValid(String email) {
        // TODO: 1/30/17 [Code Review] Due to method name, it shall only return true or false if email is valid
        // (why is this logic in View layer???), but this one also sets error messages. This is wrong.
        // setting error strings = View layer
        // validation = model/interactor layer
        if (TextUtils.isEmpty(email)) {
            ilEmail.setError(getString(R.string.empty_email_error));
            return false;
        }else {
            if (!email.contains("@")) {
                ilEmail.setError(getString(R.string.invalid_email));
                return false;
            }
        }

        hideEmailError();
        return true;
    }

    @Override
    public boolean isPasswordValid(String password) {
        if (TextUtils.isEmpty(password)){
            ilPassword.setError(getString(R.string.empty_password_error));
            return false;
        }
        hidePasswordError();
        return true;
    }

    @Override
    // TODO: 1/30/17 [Code Review] Try to get rid of passing context instance to presenter, Presenter
    // layer should know nothing about Android SDK.
    public Context getContext() {
        return this;
    }

}
