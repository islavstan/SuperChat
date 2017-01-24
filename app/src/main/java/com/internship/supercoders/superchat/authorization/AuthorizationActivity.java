package com.internship.supercoders.superchat.authorization;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.internship.supercoders.superchat.MainActivity;
import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.data.AppConsts;
import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.models.user_authorization_response.LogAndPas;
import com.internship.supercoders.superchat.registration.RegistrationActivity;
import com.internship.supercoders.superchat.utils.UserPreferences;
import com.internship.supercoders.superchat.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        ButterKnife.bind(this);

        viewUtils = new ViewUtils(this);
        authPresenter = new AuthPresenter(this);
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
    public void showChangePasswordDialog(){
        authPresenter.changePassword();
    }

    @OnClick(R.id.btn_sign_in)
    @Override
    public void onBtnSignIn() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        LogAndPas logAndPas = new LogAndPas(email, password);

        boolean isPasswordValid = isPasswordValid(password);
        boolean isEmailValid = isEmailValid(email);
        boolean isValid = isEmailValid && isPasswordValid;

        if(isValid){
            authPresenter.validateData(logAndPas);
            hidePasswordError();
            hideEmailError();
            writeUserAuthDataToDB(logAndPas);
        }
    }

    @OnClick(R.id.signup_btn)
    @Override
    public void onBtnSignUp() {
        Intent intent;
        intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    @Override
    public void writeUserAuthDataToDB(LogAndPas logAndPas) {
        DBMethods db = new DBMethods(this);

        db.readFromDB();
        db.writeAuthData(logAndPas);
    }

    @Override
    public void navigateToLogin(String token) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("token",token);
        startActivity(intent);
    }

    @Override
    public boolean isEmailValid(String email) {

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
    public Context getContext() {
        return this;
    }

}
