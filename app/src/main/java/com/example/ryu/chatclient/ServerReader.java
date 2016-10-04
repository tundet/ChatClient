package com.example.ryu.chatclient;

import android.util.Log;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by RYU on 4.10.2016.
 */
public class ServerReader implements Runnable {

    Socket socket;
    BufferedReader in;
    PrintWriter out;

    public ServerReader(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        final String TAG = "MyActivity";
        Log.i(TAG,  "Reader Created");
    }
}
