package com.internship.supercoders.superchat.api;


import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL = "http://api.quickblox.com/";
    public static final String URL_FOR_UPLOAD_AVA = "https://qbprod.s3.amazonaws.com/";

    private static Retrofit retrofit = null;
    private static Retrofit rxRetrofit = null;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            Gson result = new GsonBuilder().create();
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create(result))
                    .baseUrl(BASE_URL).build();
        }
        return retrofit;
    }

    @NonNull
    public static Retrofit getRxRetrofit() {
        if (rxRetrofit == null) {
            Gson result = new GsonBuilder().create();
            rxRetrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(result))
                    .baseUrl(BASE_URL)
                    .build();

        }
        return rxRetrofit;

    }

    public static Retrofit getRetrofit(String URL) {
        if (retrofit == null) {
            Gson result = new GsonBuilder().create();
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create(result))
                    .baseUrl(URL).build();
        }
        return retrofit;
    }
}


