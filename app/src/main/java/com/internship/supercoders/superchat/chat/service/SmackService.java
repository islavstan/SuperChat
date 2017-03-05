package com.internship.supercoders.superchat.chat.service;


import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import java.io.IOException;


public class SmackService extends Service {

    public static final String NEW_MESSAGE = "newmessage";
    public static final String SEND_MESSAGE = "sendmessage";
    public static final String NEW_ROSTER = "newroster";
    public static final String BUNDLE_FROM_JID = "b_from";
    public static final String BUNDLE_MESSAGE_BODY = "b_body";
    public static final String BUNDLE_ROSTER = "b_body";
    public static final String BUNDLE_TO = "b_to";
    public static final String MESSAGE_ID = "message_id";
    String chatId;

    public static SmackConnection.ConnectionState sConnectionState;

    public static SmackConnection.ConnectionState getState() {
        if (sConnectionState == null) {
            return SmackConnection.ConnectionState.DISCONNECTED;
        }
        return sConnectionState;
    }

    private boolean mActive;
    private Thread mThread;
    private Handler mTHandler;
    private SmackConnection mConnection;

    public SmackService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        chatId=(String) intent.getExtras().get("id");
        Log.d("stas1", chatId+ " - chatId");
        start();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop();
    }

    public void start() {
        if (!mActive) {
            mActive = true;
          Log.d("stas1", "start");
            // Create ConnectionThread Loop
            if (mThread == null || !mThread.isAlive()) {
                mThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        mTHandler = new Handler();
                        Log.d("stas1", "initConn");
                        initConnection();
                        Looper.loop();
                    }

                });
                mThread.start();
            }

        }
    }

    public void stop() {
        mActive = false;
        mTHandler.post(new Runnable() {

            @Override
            public void run() {
                if (mConnection != null) {
                    mConnection.disconnect();
                }
            }
        });

    }

    private void initConnection() {
        if (mConnection == null) {

            mConnection = new SmackConnection(this, chatId);
        }
        try {
            Log.d("stas1", "connect");
            mConnection.connect();
        } catch (IOException | SmackException | XMPPException e) {
            Log.d("stas1", "connect error - "+e.getMessage());
        }
    }
}