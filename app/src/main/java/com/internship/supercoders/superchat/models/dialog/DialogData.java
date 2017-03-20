package com.internship.supercoders.superchat.models.dialog;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class DialogData {
    @SerializedName("last_message")
    String lastMessage;
    @SerializedName("last_message_date_sent")
    String lastMessageDateSent;
    @SerializedName("last_message_user_id")
    String lastMessageUserId;
    @SerializedName("name")
    String name;
    @SerializedName("_id")
    String chatId;
    @SerializedName("photo")
    String photo;
    @SerializedName("occupants_ids")
    ArrayList<Integer> occupants_ids;
    @SerializedName("type")
    int type;
    @SerializedName("unread_messages_count")
    int unread_messages_count;
    @SerializedName("xmpp_room_jid")
    String xmpp_room_jid;

    String occupants;

    public String getOccupants() {
        return occupants;
    }

    public DialogData(String lastMessage, String lastMessageDateSent, String lastMessageUserId, String name, String chatId,
                      String photo, int type, int unread_messages_count, String xmpp_room_jid, String occupants) {
        this.lastMessage = lastMessage;
        this.lastMessageDateSent = lastMessageDateSent;
        this.lastMessageUserId = lastMessageUserId;
        this.name = name;
        this.chatId = chatId;
        this.photo = photo;
        this.type = type;
        this.unread_messages_count = unread_messages_count;
        this.xmpp_room_jid = xmpp_room_jid;
        this.occupants = occupants;
    }


    public DialogData(String lastMessage, String lastMessageDateSent, String lastMessageUserId, String name, String chatId) {
        this.lastMessage = lastMessage;
        this.lastMessageDateSent = lastMessageDateSent;
        this.lastMessageUserId = lastMessageUserId;
        this.name = name;
        this.chatId = chatId;
    }

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

    public String getChatId() {
        return chatId;
    }

    public String getPhoto() {
        return photo;
    }

    public ArrayList<Integer> getOccupants_ids() {
        return occupants_ids;
    }

    public int getType() {
        return type;
    }

    public int getUnread_messages_count() {
        return unread_messages_count;
    }

    public String getXmpp_room_jid() {
        return xmpp_room_jid;
    }
}
