package com.internship.supercoders.superchat.models.new_dialog;

import com.google.gson.annotations.SerializedName;
import com.internship.supercoders.superchat.data.ChatType;

import java.util.List;

/**
 * Created by Max on 15.03.2017.
 */

public class NewDialogBody {
    private int type;
    @SerializedName("occupants_ids")
    private String occupantsId;
    @SerializedName("name")
    private String name;
    private String photo;

    public NewDialogBody(ChatType enumType, String chatName, String photo, List<Integer> usersId) {
        if (usersId == null) {
            this.occupantsId = null;
        } else {
            String formatted = usersId.toString();
            this.occupantsId = formatted.substring(1, formatted.length() - 1).replace(" ", "");// format string for request;
        }
        this.type = enumType.getId();
        this.photo = photo;
        this.name = chatName;
    }

    public int getType() {
        return type;
    }

    public String getOccupantsId() {
        return occupantsId;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }
}