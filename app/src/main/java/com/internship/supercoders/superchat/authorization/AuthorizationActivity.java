package com.internship.supercoders.superchat.authorization;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.internship.supercoders.superchat.MainActivity;
import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.data.AppConsts;
import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.forgot_password.ForgotPasswordDialog;
import com.internship.supercoders.superchat.models.user_authorization_response.VerificationData;
import com.internship.supercoders.superchat.navigation.NavigationActivity;
import com.internship.supercoders.superchat.registration.RegistrationActivity;
import com.internship.supercoders.superchat.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthorizationActivity extends AppCompatActivity implements AuthView {

    @BindView(R.id.activity_main)
    RelativeLayout relativeLayout;

    @BindView(R.id.btn_sign_in)
    Button btnSignIn;
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

    AuthPresenterImpl authPresenter;
    ViewUtils viewUtils;
    String token;
    AuthInteractorImpl authInteractor;
    private DBMethods db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        ButterKnife.bind(this);
        db = new DBMethods(this);
        token = db.getToken();
        Log.i(AppConsts.LOG_TAG, "Session token " + token);

        viewUtils = new ViewUtils(this);
        authPresenter = new AuthPresenterImpl(this);
        authInteractor = new AuthInteractorImpl();

        authPresenter.validateUserInfo(etEmail, etPassword);


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
    public void setEmailIsEmptyError() {
        ilEmail.setError(getString(R.string.empty_email_error));
    }

    @Override
    public void setEmailIsInvalidError() {
        ilEmail.setError(getString(R.string.invalid_email));
    }

    @Override
    public void hideEmailError() {
        ilEmail.setError("");
    }

    @Override
    public void setPasswordIsEmptyError() {
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
    public void signInUser() {

    }

    @Override
    public void showAuthorizationError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setUserSignedIn() {
        authPresenter.userPreferences.kepUserSignedIn(checkBox.isChecked());
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
        ForgotPasswordDialog forgotPasswordDialog = new ForgotPasswordDialog();
        forgotPasswordDialog.show(fm, "dialog");
    }

    @OnClick(R.id.btn_sign_in)
    @Override
    public void onBtnSignIn() {
        // TODO: 1/30/17 [Code Review] This is a part of business logic, move it to interactors/model layer
        // all the validation should not be here
     /*   String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        VerificationData verificationData = new VerificationData(email, password);


        if(authInteractor.isAuthDataValid(password, email)){
            authPresenter.validateData(verificationData);
            hidePasswordError();
            hideEmailError();
            authInteractor.writeUserAuthDataToDB(verificationData);
            setUserSignedIn();
            Log.i(AppConsts.LOG_TAG, "Check Sign IN: " + Boolean.toString(authPresenter.userPreferences.isUserSignedIn()));
        }*/
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        authPresenter.signIn(db, token, email, password);
    }

    @OnClick(R.id.signup_btn)
    @Override
    public void onBtnSignUp() {
        Intent intent;
        intent = new Intent(this, RegistrationActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
    }


    @Override
    public void navigateToLogin(String token) {
        Intent intent = new Intent(this, NavigationActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    @Override
    // TODO: 1/30/17 [Code Review] Try to get rid of passing context instance to presenter, AuthPresenter
    // layer should know nothing about Android SDK.
    public Context getContext() {
        return this;
    }

    @Override
    public void hideError(int layout) {
        switch (layout) {
            case 1:
                ilEmail.setError(null);
                break;
            case 2:
                ilPassword.setError(null);
                break;

        }
    }

    @Override
    public void showPasswordLengthError() {
        ilPassword.setError(getResources().getString(R.string.password_length_error));
    }

    @Override
    public void showPasswordError() {
        ilPassword.setError(getResources().getString(R.string.password_is_weak));
    }


    @Override
    public void showEmailError() {
        ilEmail.setError(getResources().getString(R.string.invalid_email));
    }


    @Override
    public void enableLogin() {
        btnSignIn.setEnabled(true);
    }

    @Override
    public void disableLogin() {
        btnSignIn.setEnabled(false);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        authPresenter.unsubscribe();
    }
}
