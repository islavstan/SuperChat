package com.internship.supercoders.superchat.models.dialog;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by islav on 16.02.2017.
 */

public class DialogModel {
    @SerializedName("items")
    List<DialogData>list;

    public List<DialogData> getList() {
        return list;
    }
}
