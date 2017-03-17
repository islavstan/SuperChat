package com.internship.supercoders.superchat.ui.new_chat;

import com.internship.supercoders.superchat.App;
import com.internship.supercoders.superchat.api.ApiClient;
import com.internship.supercoders.superchat.models.dialog.DialogData;
import com.internship.supercoders.superchat.models.new_dialog.NewDialogBody;
import com.internship.supercoders.superchat.points.Points;
import com.internship.supercoders.superchat.ui.new_chat.interfaces.NewChatInteractor;

import rx.Observable;

/**
 * Created by Max on 13.03.2017.
 */

public class NewChatInteractorImpl implements NewChatInteractor {
    @Override
    public Observable<DialogData> createChat(NewDialogBody body) {
        final Points.CreateDialog apiCreateDialog = ApiClient.getRxRetrofit().create(Points.CreateDialog.class);
        return apiCreateDialog.createRxDialog(App.getDataBaseManager().getToken(), body);
    }
}
