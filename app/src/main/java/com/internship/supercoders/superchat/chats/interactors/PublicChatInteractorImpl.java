package com.internship.supercoders.superchat.chats.interactors;

import android.util.Log;

import com.internship.supercoders.superchat.api.ApiClient;
import com.internship.supercoders.superchat.api.ApiConstant;
import com.internship.supercoders.superchat.chats.adapters.ChatsRecyclerAdapter;
import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.models.authorization_response.Session;
import com.internship.supercoders.superchat.models.dialog.DialogModel;
import com.internship.supercoders.superchat.models.user_authorization_response.ALog;
import com.internship.supercoders.superchat.models.user_authorization_response.VerificationData;
import com.internship.supercoders.superchat.points.Points;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.subscriptions.CompositeSubscription;


public class PublicChatInteractorImpl implements PublicChatInteractor {


    public PublicChatInteractorImpl() {

    }
    @Override
    public void loadData(ChatsRecyclerAdapter adapter, String token) {
        final Points.RetrieveDialogs retrieveDialogs = ApiClient.getRetrofit().create(Points.RetrieveDialogs.class);
        Call<DialogModel> call = retrieveDialogs.retrieve(token);
        call.enqueue(new Callback<DialogModel>() {
            @Override
            public void onResponse(Call<DialogModel> call, Response<DialogModel> response) {
                if (response.isSuccessful()) {
                    DialogModel model = response.body();
                    adapter.loadChats(model.getList());
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("stas", "loadData error = " + jObjError.getString("errors"));

                    } catch (Exception e) {
                        Log.d("stas", e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<DialogModel> call, Throwable t) {

            }
        });

    }
}
