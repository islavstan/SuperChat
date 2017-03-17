package com.internship.supercoders.superchat.ui.new_chat.interfaces;

import com.internship.supercoders.superchat.models.dialog.DialogData;
import com.internship.supercoders.superchat.models.new_dialog.NewDialogBody;

import rx.Observable;

/**
 * Created by Max on 13.03.2017.
 */

public interface NewChatInteractor {
    Observable<DialogData> createChat(NewDialogBody body);
}
