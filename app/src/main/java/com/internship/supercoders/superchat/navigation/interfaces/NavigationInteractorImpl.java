package com.internship.supercoders.superchat.navigation.interfaces;


import android.util.Log;

import com.internship.supercoders.superchat.App;
import com.internship.supercoders.superchat.api.ApiClient;
import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.models.user_authorization_response.VerificationData;
import com.internship.supercoders.superchat.models.user_info.UserDataPage;
import com.internship.supercoders.superchat.models.user_update_request.UpdateUser;
import com.internship.supercoders.superchat.points.Points;
import com.internship.supercoders.superchat.users.adapter.UserRvAdapter;

import org.json.JSONObject;

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
                        }

                        ,
                        error -> Log.d("UserPresenter", "Error: " + error.toString()));


    }
}
