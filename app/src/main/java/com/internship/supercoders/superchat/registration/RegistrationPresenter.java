package com.internship.supercoders.superchat.registration;


import android.content.Context;

import java.io.File;

public interface RegistrationPresenter {
    void validateData(File file, String email, String password, String fullname, String phone, String website);
    void onDestroy();

}