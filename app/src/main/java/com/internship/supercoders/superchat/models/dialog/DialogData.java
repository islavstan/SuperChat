package com.internship.supercoders.superchat.models.dialog;

import com.google.gson.annotations.SerializedName;


public class DialogData {
    @SerializedName("last_message")
    String lastMessage;
    @SerializedName("last_message_date_sent")
    String lastMessageDateSent;
    @SerializedName("last_message_user_id")
    String lastMessageUserId;
    @SerializedName("name")
    String name;

    public String getLastMessageDateSent() {
        return lastMessageDateSent;
    }

    public String getName() {
        return name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getLastMessageUserId() {
        return lastMessageUserId;
    }
}
