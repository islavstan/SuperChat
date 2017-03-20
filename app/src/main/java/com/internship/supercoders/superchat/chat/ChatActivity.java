package com.internship.supercoders.superchat.chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.chat.adapter.ChatAdapter;
import com.internship.supercoders.superchat.chat.chat_model.MessageModel;
import com.internship.supercoders.superchat.chat.service.SmackConnection;
import com.internship.supercoders.superchat.chat.service.SmackService;
import com.internship.supercoders.superchat.db.DBMethods;
import com.vanniktech.emoji.EmojiEditText;
import com.vanniktech.emoji.EmojiPopup;
import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.listeners.OnEmojiClickedListener;
import com.vanniktech.emoji.listeners.OnEmojiPopupDismissListener;
import com.vanniktech.emoji.listeners.OnEmojiPopupShownListener;
import com.vanniktech.emoji.listeners.OnSoftKeyboardCloseListener;

import java.util.ArrayList;
import java.util.List;


public class ChatActivity extends AppCompatActivity implements ChatView {

    List<MessageModel> messageModelList = new ArrayList<>();
    Toolbar toolbar;
    RecyclerView messageRecyclerView;
    EmojiEditText editTxtMessage;
    ImageButton sendMessageBtn;
    ChatAdapter adapter;
    ChatPresenter presenter;
    private String log;
    private BroadcastReceiver mReceiver;
    LinearLayoutManager linearLayoutManager;
    DBMethods db;
    String chatId;
    private ViewGroup rootView;
    ImageButton stickerBtn;
    private EmojiPopup emojiPopup;
    private MediaPlayer smsSound;
    int myId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new DBMethods(this);
        myId = db.getMyId();
        Intent intent = getIntent();
        chatId = intent.getStringExtra("chatId");
        Log.d("stas", chatId + " chatId");
        presenter = new ChatPresenterImpl(this);
        adapter = new ChatAdapter(messageModelList, this, myId, db);
        messageRecyclerView = (RecyclerView) findViewById(R.id.list_msg);
        editTxtMessage = (EmojiEditText) findViewById(R.id.msg_type);
        sendMessageBtn = (ImageButton) findViewById(R.id.btn_chat_send);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messageRecyclerView.setLayoutManager(linearLayoutManager);
        rootView = (ViewGroup) findViewById(R.id.root);
        stickerBtn = (ImageButton) findViewById(R.id.btn_sticker);
        emojiPopup = EmojiPopup.Builder.fromRootView(rootView)
                .setOnEmojiPopupShownListener(() -> stickerBtn.setImageResource(R.drawable.emodji))
                .setOnEmojiPopupDismissListener(() -> stickerBtn.setImageResource(R.drawable.emodji_disable))

                .setOnSoftKeyboardCloseListener(() -> {
                    stickerBtn.setImageResource(R.drawable.emodji_disable);
                    emojiPopup.dismiss();
                }).build(editTxtMessage);

        smsSound = MediaPlayer.create(this, R.raw.sound);

        messageRecyclerView.setAdapter(adapter);


        loadMessages();
        sendMessageBtn.setEnabled(false);

        sendMessageBtn.setOnClickListener(v -> {
            if (editTxtMessage.getText().toString().trim().length() > 0) {
                sendMessage();
                editTxtMessage.setText("");
            }
        });


        editTxtMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editTxtMessage.getText().toString().trim().length() == 0) {
                    sendMessageBtn.setEnabled(false);
                    sendMessageBtn.setImageResource(R.drawable.send_btn_disable);
                } else if(editTxtMessage.getText().toString().trim().length() > 0) {
                    sendMessageBtn.setEnabled(true);
                    sendMessageBtn.setImageResource(R.drawable.send_btn);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        stickerBtn.setOnClickListener(v -> {
            emojiPopup.toggle();
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                switch (action) {
                    case SmackService.NEW_MESSAGE:
                        String from = intent.getStringExtra(SmackService.BUNDLE_FROM_JID);
                        String message = intent.getStringExtra(SmackService.BUNDLE_MESSAGE_BODY);
                        String messageId = intent.getStringExtra(SmackService.MESSAGE_ID);


                        adapter.addNewMessage(message, from, messageId);
                        linearLayoutManager.scrollToPosition(messageModelList.size() - 1);

                        break;


                    case SmackService.NEW_ROSTER:
                        ArrayList<String> roster = intent.getStringArrayListExtra(SmackService.BUNDLE_ROSTER);
                        if (roster == null) {
                            return;
                        }
                        for (String s : roster) {
                            log = s + "\n" + log;
                        }
                        break;
                }

            }
        };

        IntentFilter filter = new IntentFilter(SmackService.NEW_ROSTER);
        filter.addAction(SmackService.NEW_MESSAGE);
        this.registerReceiver(mReceiver, filter);
    }



 public void playSound(){
     smsSound.start();
 }

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void sendMessage() {
        Log.d("stas", "sendMessage");
        Intent intent = new Intent(SmackService.SEND_MESSAGE);
        intent.setPackage(this.getPackageName());
        intent.putExtra(SmackService.BUNDLE_MESSAGE_BODY, editTxtMessage.getText().toString());
        intent.putExtra(SmackService.BUNDLE_TO, "52822_" + chatId + "@muc.chat.quickblox.com");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        }
        this.sendBroadcast(intent);
    }


    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(mReceiver);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, SmackService.class));

    }

    @Override
    public void messageRecieved() {

    }

    @Override
    public void messageSend() {

    }

    @Override
    public void showEmptyMessageInputError() {

    }

    @Override
    public void loadMessages() {
        presenter.loadMessage(adapter, db.getToken(), chatId);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (emojiPopup != null && emojiPopup.isShowing()) {
            Log.d("stas", "  emojiPopup.dismiss()");
            emojiPopup.dismiss();
        } else {
            super.onBackPressed();
        }
    }
}