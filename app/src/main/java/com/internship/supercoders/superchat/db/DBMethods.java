package com.internship.supercoders.superchat.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.data.AppConsts;
import com.internship.supercoders.superchat.models.dialog.DialogData;
import com.internship.supercoders.superchat.models.user_authorization_response.VerificationData;
import com.squareup.picasso.Picasso;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observable;
import rx.Subscriber;


public class DBMethods {
    DBHelper dbHelper;
    DBInfo dbInfo;
    SQLiteDatabase db;
    Context context;

    public DBMethods(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
        dbInfo = new DBInfo();
        db = dbHelper.getWritableDatabase();
    }

    public SQLiteDatabase getDb() {
        return this.db;
    }

    public void writeAuthData(VerificationData verificationData) {
        ContentValues cv = new ContentValues();

        cv.put(dbInfo.emailRow, verificationData.getEmail());
        cv.put(dbInfo.passRow, verificationData.getPassword());

        long rowID = db.update(dbInfo.authTableName, cv, "id = ?", new String[]{"1"});
        Log.d(AppConsts.LOG_TAG, "row updated, ID = " + rowID);
        if (rowID == 0) {
            cv.put(dbInfo.tokenRow, "null");
            cv.put(dbInfo.timeRow, (long) 0);
            rowID = db.insert(dbInfo.authTableName, null, cv);
            Log.d(AppConsts.LOG_TAG, "row inserted, ID = " + rowID);
        }
    }

    public void readFromDB() {
        Log.d(AppConsts.LOG_TAG, "--- Rows in " + dbInfo.authTableName + ": ---");
        Cursor c = db.query(dbInfo.authTableName, null, null, null, null, null, null);

        if (c.moveToFirst()) {

            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex(dbInfo.emailRow);
            int emailColIndex = c.getColumnIndex(dbInfo.passRow);
            int tokenColIndex = c.getColumnIndex(dbInfo.tokenRow);
            int timeColIndex = c.getColumnIndex(dbInfo.timeRow);

            do {
                Log.d(AppConsts.LOG_TAG,
                        "ID = " + c.getInt(idColIndex) +
                                ", email = " + c.getString(nameColIndex) +
                                ", password = " + c.getString(emailColIndex) +
                                ", token = " + c.getString(tokenColIndex) +
                                ", time = " + Long.toString(c.getLong(timeColIndex)));
            } while (c.moveToNext());
        } else
            Log.d(AppConsts.LOG_TAG, "0 rows");
        c.close();
    }

    public VerificationData getAuthData() {
        String email;
        String password;
        Cursor c = db.query(dbInfo.authTableName, null, null, null, null, null, null);
        c.moveToFirst();
        email = c.getString(c.getColumnIndex(dbInfo.emailRow));
        password = c.getString(c.getColumnIndex(dbInfo.passRow));
        c.close();
        return new VerificationData(email, password);
    }

    public void writeToken(String token) {
        ContentValues cv = new ContentValues();
        cv.put(dbInfo.tokenRow, token);
        cv.put(dbInfo.timeRow, System.currentTimeMillis());
        long rowID = db.update(dbInfo.authTableName, cv, "id = ?", new String[]{"1"});
        Log.d(AppConsts.LOG_TAG, "row updated, ID = " + rowID);
        if (rowID == 0) {
            cv.put(dbInfo.emailRow, "null");
            cv.put(dbInfo.passRow, "null");
            rowID = db.insert(dbInfo.authTableName, null, cv);
            Log.d(AppConsts.LOG_TAG, "row inserted, ID = " + rowID);
        }
        readFromDB();
    }

    public String getToken() {
        String token;
        Cursor c = db.query(dbInfo.authTableName, null, null, null, null, null, null);
        c.moveToFirst();
        if (c.getCount() > 0)
            token = c.getString(c.getColumnIndex(dbInfo.tokenRow));
        else
            token = null;
        c.close();
        return token;
    }


    public void saveMyInfo(String photoPath, String blobId, int id, String email, String password, String fullname, String phone, String website, String facebookId) {
        ContentValues cv = new ContentValues();
        cv.put("my_id", id);
        cv.put("email", email);
        cv.put("password", password);
        cv.put("full_name", fullname);
        cv.put("phone", phone);
        cv.put("website", website);
        cv.put("facebook_id", facebookId);
        cv.put("blob_id", blobId);
        cv.put("photo_path", photoPath);
        cv.put("signed_in", 1);
        db.insert("myInfo", null, cv);


    }

    public void getMyInfoForNavigation(CircleImageView imageView, TextView email, TextView fullName) {
        Cursor c = db.rawQuery("SELECT * FROM myInfo where signed_in = '1'", null);
        for (int i = 0; i < c.getCount(); i++) {
            if (c.moveToNext()) {
                String mEmail = c.getString(c.getColumnIndex("email"));
                String mFullName = c.getString(c.getColumnIndex("full_name"));
                String photoPath = c.getString(c.getColumnIndex("photo_path"));
                Log.d("stas", "photopath = " + photoPath);
                String root = Environment.getExternalStorageDirectory().toString();
                Log.d("stas", root + "/SuperChat/ava/" + photoPath);
                Uri uri = Uri.fromFile(new File(root + "/SuperChat/ava/" + photoPath));
                Picasso.with(context).load(uri).placeholder(R.drawable.userpic).into(imageView);
                email.setText(mEmail);
                fullName.setText(mFullName);
            }
        }
        c.close();

    }

    public boolean isLoginUser() {
        Cursor c = db.rawQuery("SELECT * FROM myInfo where signed_in = '1'", null);
        boolean exists = (c.getCount() > 0);
        c.close();
        return exists;
    }

    public void signOut() {
        String strSQL = "UPDATE myInfo SET signed_in = '0' WHERE signed_in = '1'";
        db.execSQL(strSQL);
    }

    public void saveImagePath(String path, String id) {
        String strSQL = "UPDATE myInfo SET photo_path = '" + path + "' WHERE my_id = '" + id + "'";
        db.execSQL(strSQL);
    }

    public String getEmail() {
        Cursor c = db.rawQuery("SELECT * FROM myInfo where signed_in = '1'", null);
        String email = null;
        for (int i = 0; i < c.getCount(); i++) {
            if (c.moveToNext()) {
                email = c.getString(c.getColumnIndex("email"));
            }
        }
        c.close();
        return email;

    }


    public Observable<VerificationData>getEmailAndPassword() {
        return Observable.create(new Observable.OnSubscribe<VerificationData>() {
            @Override
            public void call(Subscriber<? super VerificationData> subscriber) {
                Cursor c = db.rawQuery("SELECT * FROM myInfo where signed_in = '1'", null);
                subscriber.onNext(c.moveToFirst() ? new VerificationData(c.getString(c.getColumnIndex("email")),
                        c.getString(c.getColumnIndex("password"))) : null);
                c.close();
            }
        });
    }

    public String getPassword() {
        Cursor c = db.rawQuery("SELECT * FROM myInfo where signed_in = '1'", null);
        String password = null;
        for (int i = 0; i < c.getCount(); i++) {
            if (c.moveToNext()) {
                password = c.getString(c.getColumnIndex("password"));
            }
        }
        c.close();
        return password;

    }


    public Observable<Long> writeChatsData(DialogData dialogData) {
        return Observable.create(subscriber -> {
            ContentValues contentValues = new ContentValues();
            contentValues.put("chat_id", dialogData.getChatId());
            contentValues.put("last_message", dialogData.getLastMessage());
            contentValues.put("last_message_date_sent", dialogData.getLastMessageDateSent());
            contentValues.put("last_message_user_id", dialogData.getLastMessageUserId());
            contentValues.put("occupants_ids", dialogData.getOccupants());
            contentValues.put("name", dialogData.getName());
            contentValues.put("photo", dialogData.getPhoto());
            contentValues.put("type", dialogData.getType());
            contentValues.put("unread_messages_count", dialogData.getUnread_messages_count());
            contentValues.put("xmpp_room_jid", dialogData.getXmpp_room_jid());
            subscriber.onNext(db.insert("myChats", null, contentValues));
        });


    }
}
