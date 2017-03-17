package com.internship.supercoders.superchat.navigation.interfaces;


import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.internship.supercoders.superchat.App;
import com.internship.supercoders.superchat.api.ApiClient;
import com.internship.supercoders.superchat.authorization.AuthInteractor;
import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.models.user_authorization_response.VerificationData;
import com.internship.supercoders.superchat.models.user_info.UserDataPage;
import com.internship.supercoders.superchat.models.user_update_request.UpdateUser;
import com.internship.supercoders.superchat.points.Points;
import com.internship.supercoders.superchat.users.adapter.UserRvAdapter;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NavigationInteractorImpl implements NavigationInteractor {
    @Override
    public void getUserData() {

    }

    @Override
    public void signOut(DBMethods dbMethods, NavigationFinishedListener listener) {
        final Points.SignOut signOut = ApiClient.getRxRetrofit().create(Points.SignOut.class);


        Observable.concat(signOut.destroySession(dbMethods.getToken()), signOut.signOut(dbMethods.getToken()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isSuccessful()) {
                        dbMethods.signOut();
                        listener.finishSignOut();

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Log.d("stas", "signOut error = " + jObjError.getString("errors"));

                        } catch (Exception e) {
                            Log.d("stas", e.getMessage());
                        }

                    }


                });


    }

    @Override
    public void loadUsers(DBMethods dbMethods) {
        final Points.RxRetriveAllUsers apiService = ApiClient.getRxRetrofit().create(Points.RxRetriveAllUsers.class);
        apiService.getUserInfoPage(dbMethods.getToken(), "1", "100")
                .map(UserDataPage::getUserList)
                .flatMap(Observable::from)
                .map(UserDataPage.UserDataList::getItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userDataFullProfile -> {
                            dbMethods.writeUserToDb(userDataFullProfile)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(result -> Log.d("stas", "writeUserToDb = " + result),
                                            error -> Log.d("stas", "writeUserToDb error" + error.toString()));

                            if (userDataFullProfile.getBlobId() != null) {
                                Log.d("stas","getBlobId");
                                dbMethods.checkPhoto(userDataFullProfile.getBlobId())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(result -> {
                                            if (result == 0) {
                                                Log.d("stas","downloadUserPhoto");
                                                downloadUserPhoto(dbMethods, Integer.toString(userDataFullProfile.getId()), userDataFullProfile.getBlobId(), dbMethods.getToken());
                                            }
                                        }, error -> Log.d("stas", "checkPhoto error = " + error.getMessage()));


                            }

                        }

                        ,
                        error -> Log.d("UserPresenter", "Error: " + error.toString()));


    }


    public String randomName() {
        Random r = new Random();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";

        final int N = 10;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            sb.append(alphabet.charAt(r.nextInt(alphabet.length())));
        }
        String randomName = sb.toString();
        return randomName + ".jpeg";
    }


    public void downloadUserPhoto(DBMethods db, String userId, String blobId, String token) {
        final Points.DownloadFilePoint downloadFilePoint = ApiClient.getRetrofit().create(Points.DownloadFilePoint.class);
        Call<ResponseBody> call = downloadFilePoint.downloadFile(blobId, "0.1.0", token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String name = randomName();
                    //  boolean writtenToDisk = writeResponseBodyToDisk(response.body(), name);
                    new WriteImageToDisc().execute(response.body(), name);

                    // Log.d("stas", "file download was a success? " + writtenToDisk);

                    db.saveImage(name, userId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe();


                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("stas", "downloadUserPhoto error = " + jObjError.getString("errors"));

                    } catch (Exception e) {
                        Log.d("stas", e.getMessage());
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("stas", t.getMessage());
            }
        });


    }

    class WriteImageToDisc extends AsyncTask<Object, Void, Void> {


        @Override
        protected Void doInBackground(Object... params) {
            ResponseBody body = (ResponseBody) params[0];
            String name = (String) params[1];


            try {
                // todo change the file location/name according to your needs
                String root = Environment.getExternalStorageDirectory().toString();
                File dir = new File(root + "/SuperChat/ava/");
                if (!dir.exists()) dir.mkdirs();
                File fTo = new File(root + "/SuperChat/ava/" + name);
                InputStream inputStream = null;
                OutputStream outputStream = null;

                try {
                    byte[] fileReader = new byte[4096];

                    long fileSize = body.contentLength();
                    long fileSizeDownloaded = 0;

                    inputStream = body.byteStream();
                    outputStream = new FileOutputStream(fTo);

                    while (true) {
                        int read = inputStream.read(fileReader);

                        if (read == -1) {
                            break;
                        }

                        outputStream.write(fileReader, 0, read);

                        fileSizeDownloaded += read;

                        Log.d("stas", "file download: " + fileSizeDownloaded + " of " + fileSize);
                    }

                    outputStream.flush();


                } catch (IOException e) {

                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }

                    if (outputStream != null) {
                        outputStream.close();
                    }
                }
            } catch (IOException e) {

            }
            return null;
        }
    }


}
