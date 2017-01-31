package com.internship.supercoders.superchat.registration;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.internship.supercoders.superchat.MainActivity;
import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.utils.InternetConnection;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.squareup.picasso.Picasso;
import com.vicmikhailau.maskededittext.MaskedEditText;

import org.json.JSONException;
import org.json.JSONObject;

import id.zelory.compressor.Compressor;
import io.fabric.sdk.android.Fabric;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func3;
import rx.subscriptions.CompositeSubscription;

import static android.R.attr.theme;
import static android.R.attr.thumbnail;

public class RegistrationActivity extends AppCompatActivity implements RegistrationView {
    private EditText emailET, passwordET, confPassET, fullnameET, websiteET;
    MaskedEditText phoneET;
    private Button signupBtn;
    private Button facebookBtn;
    private CircleImageView userPhoto;
    private Toolbar toolbar;
    TextInputLayout inputPasswordLayout;
    TextInputLayout inputConfPassLayout;
    TextInputLayout inputEmailLayout;
    RegistrationPresenter registrationPresenter;
    ProgressBar progressbar;
    private static final int SELECT_PICTURE = 100;
    private static final int REQUEST_CAMERA = 200;
    Uri selectedImageUri;
    File photoFile;
    File actualImage;
    LoginButton logBtn;
    CallbackManager callbackManager;
    String facebookId;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_registration);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        callbackManager = CallbackManager.Factory.create();
        logBtn = (LoginButton) findViewById(R.id.login_button);
        facebookBtn = (Button) findViewById(R.id.link_facebook_btn);


        emailET = (EditText) findViewById(R.id.input_email);
        passwordET = (EditText) findViewById(R.id.input_password);
        confPassET = (EditText) findViewById(R.id.input_confirm_password);
        fullnameET = (EditText) findViewById(R.id.input_fullname);
        phoneET = (MaskedEditText) findViewById(R.id.input_phone);
        websiteET = (EditText) findViewById(R.id.input_website);
        userPhoto = (CircleImageView) findViewById(R.id.photo);
        signupBtn = (Button) findViewById(R.id.signup_btn);
        inputPasswordLayout = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputConfPassLayout = (TextInputLayout) findViewById(R.id.input_layout_password2);
        inputEmailLayout = (TextInputLayout) findViewById(R.id.input_layout_email);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);

        userPhoto = (CircleImageView) findViewById(R.id.photo);
        userPhoto.setOnClickListener(view -> showDialogForCameraOrGallery());

        registrationPresenter = new RegistrationPresenterImpl(this, new RegistrInteractorImpl());


        facebookBtn.setOnClickListener(view -> {
                    if (InternetConnection.hasConnection(RegistrationActivity.this))
                        registrationPresenter.facebookLogin(logBtn, callbackManager);
                    else showInternetConnectionError();
                }


        );


        registrationPresenter.validateUserInfo(emailET, passwordET, confPassET, signupBtn);


        signupBtn.setOnClickListener(view -> {
            // TODO: 1/30/17 [Code Review] part of business logic
            if (InternetConnection.hasConnection(this)) {
                String email = emailET.getText().toString().trim();
                String password = passwordET.getText().toString().trim();
                String conf_password = confPassET.getText().toString().trim();
                String fullname = fullnameET.getText().toString().trim();
                String phone = "380" + phoneET.getUnMaskedText();
                String website = websiteET.getText().toString().trim();
                registration(token, photoFile, email, password, conf_password, fullname, phone, website, facebookId);
            } else showInternetConnectionError();
        });

    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
        registrationPresenter.unsubscribe();
    }

    @Override
    public void showProgress() {
        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressbar.setVisibility(View.INVISIBLE);
    }


    @Override
    public void navigateToLogin(String token) {
        Toast.makeText(this, "registration successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
        finish();
    }


    @Override
    public void registration(String token, File photo, String email, String password, String conf_password, String fullname, String phone, String website, String facebookId) {
        registrationPresenter.validateData(token, photo, email, password, fullname, phone, website, facebookId);
    }

    @Override
    public void showRegistrationError() {
        Toast.makeText(this, "Registration Error", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }


    @Override
    public void changeFacebookBtnText(String facebookId) {
        facebookBtn.setText(getResources().getString(R.string.linked_facebook_btn));
        this.facebookId = facebookId;
    }

    @Override
    public void showRegistrationErrorWithToken(String token, String error) {
        if (this.token == null) {
            this.token = token;
        }
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }


    @Override
    public Context getContext() {
        return RegistrationActivity.this;
    }


    @Override
    public void hideError(int layout) {
        switch (layout) {
            case 1:
                inputEmailLayout.setError(null);
                break;
            case 2:
                inputPasswordLayout.setError(null);
                break;
            case 4:
                inputConfPassLayout.setError(null);
                break;
        }
    }

    @Override
    public void showConfirmPasswordError() {
        inputConfPassLayout.setError(getResources().getString(R.string.password_is_not_the_same));
    }

    @Override
    public void showPasswordLengthError(int layout) {
        switch (layout) {
            case 1:
                inputPasswordLayout.setError(getResources().getString(R.string.password_length_error));
                break;
            case 2:
                inputConfPassLayout.setError(getResources().getString(R.string.password_length_error));
                break;
        }
    }

    @Override
    public void showPasswordError(int layout) {
        switch (layout) {
            case 1:
                inputPasswordLayout.setError(getResources().getString(R.string.password_is_weak));
                break;
            case 2:
                inputConfPassLayout.setError(getResources().getString(R.string.password_is_weak));
                break;

        }
    }

    @Override
    public void showEmailError() {
        inputEmailLayout.setError(getResources().getString(R.string.invalid_email));
    }


    @Override
    public void enableSignUp() {
        signupBtn.setEnabled(true);
    }

    @Override
    public void disableSignUp() {
        signupBtn.setEnabled(false);

    }


    @Override
    public void showDialogForCameraOrGallery() {
        final String[] choiceList = {"Device Camera", "Photo Gallery", "Back"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo");
        builder.setItems(choiceList, (dialog, item) -> {
            switch (item) {
                case 0:
                    openCamera();
                    break;
                case 1:
                    openImageChooser();
                    break;
                case 2:
                    dialog.cancel();
                    break;
            }

        });
        builder.setCancelable(true);
        AlertDialog alert = builder.create();
        alert.show();


    }

    @Override
    public void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void setPhotoFromCamera(File photo, Bitmap photoBitmap) {
        userPhoto.setImageBitmap(photoBitmap);
        photoFile = photo;
    }

    @Override
    public void showInternetConnectionError() {
        Toast.makeText(this, "internet connection error", Toast.LENGTH_SHORT).show();
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // TODO: 1/30/17 [Code Review] business logic, move to interactor layer

                selectedImageUri = data.getData();
                Picasso.with(this).load(selectedImageUri).into(userPhoto);
                actualImage = new File(getRealPathFromURI(this, selectedImageUri));
                photoFile = Compressor.getDefault(this).compressToFile(actualImage);


            }
            if (requestCode == REQUEST_CAMERA) {
                registrationPresenter.makePhotoFromCamera(data, this.getCacheDir());

            }
        }
    }
}
