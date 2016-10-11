package com.example.ryu.chatclient;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by RYU on 4.10.2016.
 */
public class ServerReader implements Runnable {

    Socket socket;
    ChatHistory history;
    BufferedReader in;
    PrintWriter out;

    public ServerReader(Socket socket, ChatHistory history){

        this.socket = socket;
        this.history = history;
    }

    @Override
    public void run(){

        System.out.println("HELLOO");

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Server reader started.");
            while (!socket.isClosed()) {
                String input = in.readLine();
                System.out.println("Server Reader: " + input);
                if (input != null && input.length() > 1) {
                    System.out.println("Server Reader after if: " + input);
                    int idx = input.indexOf(']');
                    int idx2 = input.indexOf(':'); //TODO error handling
                    String timeStamp = input.substring(0,idx+1);
                    String username = input.substring(idx+1, idx2);
                    String message = input.substring(idx2+1);
                    ChatMessage chatMessage = new ChatMessage(timeStamp, username, message);
                    history.insert(chatMessage);
                }else{
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error");
        }
        System.out.println("Server Reader ended");
        Log.i("ServerReader", "created");
    }
}
