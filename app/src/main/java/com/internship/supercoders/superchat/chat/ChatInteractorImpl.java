package com.internship.supercoders.superchat.chat;

import android.util.Log;

import com.internship.supercoders.superchat.api.ApiClient;
import com.internship.supercoders.superchat.chat.adapter.ChatAdapter;
import com.internship.supercoders.superchat.chat.chat_model.Message;
import com.internship.supercoders.superchat.points.Points;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ChatInteractorImpl implements ChatInteractor {


    @Override
    public void loadMessages(ChatAdapter adapter, String token, String chatId) {
        final Points.RetrieveMessages apiService = ApiClient.getRxRetrofit().create(Points.RetrieveMessages.class);
        Observable<Response<Message>> messageObservable = apiService.retrieveMessages(token, chatId);
        messageObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(messageResponse -> {
                            if (messageResponse.isSuccessful()) {
                                Log.d("stas1", "loadMessages ok");
                                Message message = messageResponse.body();
                                adapter.retrieveMessages(message.getMessageModelList());
                            }
                        }


                );


    }
}