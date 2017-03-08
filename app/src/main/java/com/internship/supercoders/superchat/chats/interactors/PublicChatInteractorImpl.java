package com.internship.supercoders.superchat.chats.interactors;

import android.graphics.LightingColorFilter;
import android.util.Log;

import com.internship.supercoders.superchat.api.ApiClient;
import com.internship.supercoders.superchat.api.ApiConstant;
import com.internship.supercoders.superchat.chats.adapters.ChatsRecyclerAdapter;
import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.models.authorization_response.Session;
import com.internship.supercoders.superchat.models.dialog.DialogData;
import com.internship.supercoders.superchat.models.dialog.DialogModel;
import com.internship.supercoders.superchat.models.user_authorization_response.ALog;
import com.internship.supercoders.superchat.models.user_authorization_response.VerificationData;
import com.internship.supercoders.superchat.points.Points;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class PublicChatInteractorImpl implements PublicChatInteractor {


    public PublicChatInteractorImpl() {
//hello
    }

    @Override
    public void loadData(ChatsRecyclerAdapter adapter, DBMethods db) {
        final Points.RetrieveDialogs retrieveDialogs = ApiClient.getRetrofit().create(Points.RetrieveDialogs.class);
        Call<DialogModel> call = retrieveDialogs.retrieve(db.getToken());
        call.enqueue(new Callback<DialogModel>() {
            @Override
            public void onResponse(Call<DialogModel> call, Response<DialogModel> response) {
                if (response.isSuccessful()) {
                    DialogModel model = response.body();
                    List<DialogData> dataList = model.getList();
                    for (int i = 0; i < dataList.size(); i++) {
                        DialogData data = dataList.get(i);
                        String lastMessage = data.getLastMessage();
                        Log.d("stas", lastMessage + " lastMessage");
                        String chatId = data.getChatId();
                        Log.d("stas", chatId + " chatId");
                        String name = data.getName();
                        Log.d("stas", name + " name");
                        List<Integer> list = data.getOccupants_ids();
                        String occupants = "";
                        for (int c = 0; c < list.size(); c++) {
                            occupants = occupants + list.get(c);
                            if (c < list.size() - 1)
                                occupants = occupants + ",";
                        }
                        Log.d("stas", occupants);

                        String finalOccupants = occupants;
                        Log.d("stas", chatId + " chat id ");
                        db.checkChat(chatId)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(integer -> {
                                    Log.d("stas", integer + " integer");
                                            if (integer == 0) {

                                                db.writeChatsData(data, finalOccupants).
                                                        subscribeOn(Schedulers.io())
                                                        .observeOn(AndroidSchedulers.mainThread())
                                                        .subscribe(result -> Log.d("stas", result + " - write chat in db"));
                                            } else Log.d("stas", "chat exists in db");
                                        }


                                );
                    }




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
