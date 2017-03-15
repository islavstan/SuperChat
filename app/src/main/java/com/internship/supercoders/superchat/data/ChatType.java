package com.internship.supercoders.superchat.data;

/**
 * Created by Max on 15.03.2017.
 */

public enum ChatType {
    PUBLIC_GROUP(1),
    GROUP(2),
    PRIVATE(3);
    private int id;

    ChatType(int value) {
        this.id = value;
    }

    public int getId() {
        return id;
    }
}
