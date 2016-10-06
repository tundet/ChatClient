package com.example.ryu.chatclient;

/**
 * Created by RYU on 4.10.2016.
 */
public class ChatMessage {

    /*Instance variables for ChatMessage*/
    String message;
    String username;
    public boolean left;

    /*Constructor for ChatMessage*/
    ChatMessage(boolean left, String username, String message) {
        super();
        this.left = left;
        this.username = username;
        this.message = message;
    }

    /*toString() method, returns the message*/
    @Override
    public String toString() {
        return this.message;
    }

}