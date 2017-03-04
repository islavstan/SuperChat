package com.internship.supercoders.superchat.chat.service;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.internship.supercoders.superchat.db.DBMethods;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.DefaultExtensionElement;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.ping.PingFailedListener;
import org.jivesoftware.smackx.ping.PingManager;

import java.io.IOException;
import java.util.Collection;
import java.util.Random;

public class SmackConnection implements ConnectionListener, ChatManagerListener, RosterListener, ChatMessageListener, PingFailedListener, MessageListener {

    public static enum ConnectionState {
        CONNECTED, CONNECTING, RECONNECTING, DISCONNECTED;
    }

    private XMPPTCPConnection mConnection;
    private final Context mApplicationContext;
    private BroadcastReceiver mReceiver;
    String chatId;
    MultiUserChat muc;
    Message msg;
    DefaultExtensionElement extensionElement;
    MultiUserChatManager manager;
    DBMethods dbMethods;
    String service = "chat.quickblox.com";

    public SmackConnection(Context pContext, String chatId) {
        mApplicationContext = pContext.getApplicationContext();
        dbMethods = new DBMethods(pContext.getApplicationContext());
        this.chatId = chatId;


    }

    public void connect() throws IOException, XMPPException, SmackException {

        XMPPTCPConnectionConfiguration builder = XMPPTCPConnectionConfiguration.builder()
                .setUsernameAndPassword(dbMethods.getEmail(), dbMethods.getPassword())
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .setServiceName(service)
                .setPort(5222)
                .build();
        mConnection = new XMPPTCPConnection(builder);
        mConnection.addConnectionListener(this);
        mConnection.connect();
        mConnection.login();

        manager = MultiUserChatManager.getInstanceFor(mConnection);
        muc = manager.getMultiUserChat("52822_" + chatId + "@muc.chat.quickblox.com");
        muc.join(mConnection.getUser());

        PingManager.setDefaultPingInterval(600); //Ping every 10 minutes
        PingManager pingManager = PingManager.getInstanceFor(mConnection);
        pingManager.registerPingFailedListener(this);

        setupSendMessageReceiver();
        //ChatManager.getInstanceFor(mConnection).addChatListener(this);

        muc.addMessageListener(this);
    }

    private void setupSendMessageReceiver() {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals(SmackService.SEND_MESSAGE)) {
                    sendMessage(intent.getStringExtra(SmackService.BUNDLE_MESSAGE_BODY), intent.getStringExtra(SmackService.BUNDLE_TO));
                }
            }

        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(SmackService.SEND_MESSAGE);
        mApplicationContext.registerReceiver(mReceiver, filter);
    }



    public void disconnect() {
        if(mConnection != null){
            mConnection.disconnect();
        }

        mConnection = null;
        if(mReceiver != null){
            mApplicationContext.unregisterReceiver(mReceiver);
            mReceiver = null;
        }
    }

    public String randomName() {
        Random r = new Random(); // just create one and keep it around
        String alphabet = "abcd1e2fghijklm0no6pqrs7t9uvwxy8z";

        final int N = 10;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            sb.append(alphabet.charAt(r.nextInt(alphabet.length())));
        }
        String randomName = sb.toString();

        return randomName;
    }

  /*  private String getDate(){
    }
*/

    private void sendMessage(String body, String to) {
        msg = new Message();
        extensionElement =new DefaultExtensionElement("extraParams","jabber:client");
        extensionElement.setValue("save_to_history","1");
        // extensionElement.setValue("date_sent",getDate());
        Log.d("stas",msg.getBody()+" = getBody");
        msg.setBody(body);
        Log.d("stas",body+" - body");
        msg.setStanzaId(randomName());
        msg.setType(Message.Type.groupchat);
        msg.setTo(to);
        msg.addExtension(extensionElement);
        try {
            muc.sendMessage(msg);



            Log.d("stas", "sendMessage - "+msg.toXML().toString());

        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connected(XMPPConnection connection) {

    }

    @Override
    public void authenticated(XMPPConnection connection, boolean resumed) {

    }

    @Override
    public void connectionClosed() {

    }

    @Override
    public void connectionClosedOnError(Exception e) {

    }

    @Override
    public void reconnectionSuccessful() {

    }

    @Override
    public void reconnectingIn(int seconds) {

    }

    @Override
    public void reconnectionFailed(Exception e) {

    }

    @Override
    public void chatCreated(Chat chat, boolean createdLocally) {
        chat.addMessageListener(this);
    }

    @Override
    public void processMessage(Chat chat, Message message) {

    }

    @Override
    public void processMessage(Message message) {
        if (message.getBody() != null) {
            DefaultExtensionElement extensionElement = message.getExtension("extraParams", "jabber:client");
            String messageId = extensionElement.getValue("message_id");
            Log.d("stas", messageId + " = mesId");
            Log.d("stas", "processMessage - " + message.toXML().toString());
            Intent intent = new Intent(SmackService.NEW_MESSAGE);
            intent.setPackage(mApplicationContext.getPackageName());
            intent.putExtra(SmackService.MESSAGE_ID, messageId);
            intent.putExtra(SmackService.BUNDLE_MESSAGE_BODY, message.getBody());
            intent.putExtra(SmackService.BUNDLE_FROM_JID, message.getFrom());
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
            }
            mApplicationContext.sendBroadcast(intent);

        }
    }




    @Override
    public void entriesAdded(Collection<String> addresses) {

    }

    @Override
    public void entriesUpdated(Collection<String> addresses) {

    }

    @Override
    public void entriesDeleted(Collection<String> addresses) {

    }

    @Override
    public void presenceChanged(Presence presence) {

    }

    @Override
    public void pingFailed() {

    }
}