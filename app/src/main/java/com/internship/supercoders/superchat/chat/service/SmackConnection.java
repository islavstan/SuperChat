package com.internship.supercoders.superchat.chat.service;


import android.content.BroadcastReceiver;
import android.content.Context;

import com.internship.supercoders.superchat.db.DBMethods;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.DefaultExtensionElement;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.ping.PingFailedListener;

import java.util.Collection;

public class SmackConnection implements ConnectionListener, ChatManagerListener, RosterListener, ChatMessageListener, PingFailedListener, MessageListener {

    public static enum ConnectionState {
        CONNECTED, CONNECTING, RECONNECTING, DISCONNECTED;
    }

    private XMPPTCPConnection mConnection;
    private final Context mApplicationContext;
    private BroadcastReceiver mReceiver;

    MultiUserChat muc;
    Message msg;
    DefaultExtensionElement extensionElement;
    MultiUserChatManager manager;
    DBMethods dbMethods;


    public SmackConnection(Context pContext) {
        mApplicationContext = pContext.getApplicationContext();
        dbMethods = new DBMethods(pContext);

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
    public void processMessage(Message message) {

    }

    @Override
    public void chatCreated(Chat chat, boolean createdLocally) {

    }

    @Override
    public void processMessage(Chat chat, Message message) {

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
