package com.example.ryu.chatclient;

/**
 * This class is used for sending messages written by the client to the server.
 * @authors
 * Group Tableflipz
 * 1402803 Jämiä Mikko
 * 1406733 Järvinen Otto
 * 1503524 Taba Tünde
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerWriter implements Runnable {

    /*Class member variables*/
    Socket socket;
    ChatMessage chatMessage;
    PrintWriter out;
    Thread thread;

    /*Constructor*/
    public ServerWriter(Socket socket, ChatMessage chatMessage){
        this.socket = socket;
        this.chatMessage = chatMessage;
        /*Initialize output stream and give socket*/
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();//oops
        }
        /*Start new thread*/
        thread = new Thread(this);
        thread.start();
    }

    /*Runnable class' run() method*/
    @Override
    public void run(){
        /*Give message to output and thus to the server*/
        out.println(chatMessage.timestamp + chatMessage.username + ": " + chatMessage.message);
        System.out.println("ServerWriter: " + out.checkError());//check whether there was an error
    }
}