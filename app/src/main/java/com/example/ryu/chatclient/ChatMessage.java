package com.example.ryu.chatclient;

/**
* This class is the blueprint for the chat messages that we send around.
 * @authors
 * Group Tableflipz
 * 1402803 Jämiä Mikko
 * 1406733 Järvinen Otto
 * 1503524 Taba Tünde
 */

public class ChatMessage {

    /*Instance variables for ChatMessage*/
    String message;
    String username;
    String timestamp;

    /*Constructor for ChatMessage*/
    ChatMessage(String timestamp, String username, String message) {
        super();
        this.timestamp = timestamp;
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