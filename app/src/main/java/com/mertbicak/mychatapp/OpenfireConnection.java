package com.mertbicak.mychatapp;

import android.os.AsyncTask;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class OpenfireConnection extends AsyncTask {




    @Override
    protected Object doInBackground(Object[] objects) {


        XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
        configBuilder.setUsernameAndPassword("user1", "mertmert");
        configBuilder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);

        try {
            configBuilder.setXmppDomain("192.168.1.22");
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }
        try {
            configBuilder.setResource("/usr/local/openfire");
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }
        try {
            configBuilder.setHostAddress(InetAddress.getByName(Config.HOST));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        configBuilder.setPort(5222);

        AbstractXMPPConnection connection = new XMPPTCPConnection(configBuilder.build());
        try {
            connection.connect().login();


            ChatManager chatManager = ChatManager.getInstanceFor(connection);
            chatManager.addIncomingListener(new IncomingChatMessageListener() {
                @Override
                public void newIncomingMessage(EntityBareJid from, Message message, org.jivesoftware.smack.chat2.Chat chat) {
                    System.out.println("New message from " + from + ": " + message.getBody());
                }

            });


            EntityBareJid jid = JidCreate.entityBareFrom("user1@" + Config.HOST);
            org.jivesoftware.smack.chat2.Chat chat = chatManager.chatWith(jid);
            chat.send("Howdy!");




        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (SmackException.NotConnectedException e1) {
            e1.printStackTrace();
        } catch (SmackException e1) {
            e1.printStackTrace();
        }  catch (XMPPException e1) {
            e1.printStackTrace();
        }



        return null;
    }
}

