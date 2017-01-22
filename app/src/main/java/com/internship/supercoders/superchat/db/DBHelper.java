package com.internship.supercoders.superchat.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.internship.supercoders.superchat.data.AppConsts.LOG_TAG;

/**
 * Created by RON on 21.01.2017.
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "SuperChat", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        createAuthTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createAuthTable(SQLiteDatabase db){
        DBInfo dbInfo = new DBInfo();
        db.execSQL("create table " + dbInfo.authTableName + " ("
                + "id integer primary key autoincrement,"
                + dbInfo.emailRow + " text,"
                + dbInfo.passRow + " text" + ");");
    }
}
