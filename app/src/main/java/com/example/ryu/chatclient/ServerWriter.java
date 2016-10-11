package com.example.ryu.chatclient;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by RYU on 4.10.2016.
 */
public class ServerWriter implements Runnable {

    Socket socket;
    ChatMessage chatMessage;
    PrintWriter out;
    Thread thread;

    public ServerWriter(Socket socket, ChatMessage chatMessage){

        this.socket = socket;
        this.chatMessage = chatMessage;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run(){

        out.println(chatMessage.timestamp + chatMessage.username + ": " + chatMessage.message); //TODO cleanup
        System.out.println("ServerWriter: " + out.checkError());
        System.out.println("ServerWriter: " + chatMessage.timestamp + chatMessage.username + ": " + chatMessage.message);
        Log.i("ServerWriter", "created");
    }
}