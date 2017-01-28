package com.internship.supercoders.superchat.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.internship.supercoders.superchat.data.AppConsts;
import com.internship.supercoders.superchat.models.user_authorization_response.LogAndPas;

/**
 * Created by RON on 21.01.2017.
 */
public class DBMethods {
    DBHelper dbHelper;
    DBInfo dbInfo;
    SQLiteDatabase db;

    public DBMethods(Context context) {
        dbHelper = new DBHelper(context);
        dbInfo = new DBInfo();
        db = dbHelper.getWritableDatabase();
    }

    public SQLiteDatabase getDb(){
       return this.db;
    }

    public void writeAuthData(LogAndPas logAndPas) {
        ContentValues cv = new ContentValues();

        cv.put(dbInfo.emailRow, logAndPas.getEmail());
        cv.put(dbInfo.passRow, logAndPas.getPassword());

        long rowID = db.update(dbInfo.authTableName, cv, "id = ?", new String[]{"1"});
        Log.d(AppConsts.LOG_TAG, "row updated, ID = " + rowID);
        if(rowID==0){
            rowID = db.insert(dbInfo.authTableName, null, cv);
            Log.d(AppConsts.LOG_TAG, "row inserted, ID = " + rowID);
        }
    }

    public void readFromDB(){
        Log.d(AppConsts.LOG_TAG, "--- Rows in " + dbInfo.authTableName + ": ---");
        Cursor c = db.query(dbInfo.authTableName, null, null, null, null, null, null);

        if (c.moveToFirst()) {

            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex(dbInfo.emailRow);
            int emailColIndex = c.getColumnIndex(dbInfo.passRow);

            do {
                Log.d(AppConsts.LOG_TAG,
                        "ID = " + c.getInt(idColIndex) +
                                ", email = " + c.getString(nameColIndex) +
                                ", password = " + c.getString(emailColIndex));
            } while (c.moveToNext());
        } else
            Log.d(AppConsts.LOG_TAG, "0 rows");
        c.close();
    }
    public LogAndPas getAuthData(){
        String email;
        String password;
        Cursor c = db.query(dbInfo.authTableName, null, null, null, null, null, null);
        c.moveToFirst();
        email = c.getString(c.getColumnIndex(dbInfo.emailRow));
        password = c.getString(c.getColumnIndex(dbInfo.passRow));
        c.close();
        return new LogAndPas(email, password);
    }
}
