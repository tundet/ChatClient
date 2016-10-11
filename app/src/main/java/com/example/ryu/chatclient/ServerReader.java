package com.example.ryu.chatclient;

/**
 * This class is used for reading the messages that are received from the server side.
 * @authors
 * Group Tableflipz
 * 1402803 Jämiä Mikko
 * 1406733 Järvinen Otto
 * 1503524 Taba Tünde
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerReader implements Runnable {

    /*Class member variables*/
    Socket socket;
    ChatHistory history;
    BufferedReader in;
    PrintWriter out;

    /*Class constructor*/
    public ServerReader(Socket socket, ChatHistory history){
        this.socket = socket;
        this.history = history;
    }

    /*ServerReader is a runnable class so it has a run() method*/
    @Override
    public void run(){
        try {
            /*Initialize the input stream from the given socket*/
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            /*While the socket is open read the lines coming from the input stream*/
            while (!socket.isClosed()) {
                String input = in.readLine();
                /*If there is an input that is not empty, parse it into a message object*/
                if (input != null && input.length() > 1) {
                    int idx = input.indexOf(']'); //end of timestamp
                    int idx2 = input.indexOf(':'); //end of username
                    String timeStamp = input.substring(0,idx+1); //set timestamp [HH.mm]
                    String username = input.substring(idx+1, idx2); //set username that comes after timestamp
                    String message = input.substring(idx2+1); //message is everything that comes after the username
                    ChatMessage chatMessage = new ChatMessage(timeStamp, username, message);
                    history.insert(chatMessage); //insert message into observable class
                }else{
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();//oops
        }
    }
}
