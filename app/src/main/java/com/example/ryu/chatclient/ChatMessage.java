package com.example.ryu.chatclient;

/**
 * Created by RYU on 4.10.2016.
 */
public class ChatMessage {

    /*Instance variables for ChatMessage*/
    String message;
    String username;

    /*Constructor for ChatMessage*/
    ChatMessage(String username, String message) {
        super();
        this.username = username;
        this.message = message;
    }

    /*toString() method, returns the message*/
    @Override
    public String toString() {
        String tmp = new String(message);
        tmp.replace('#','\n');

        return tmp;
    }

}