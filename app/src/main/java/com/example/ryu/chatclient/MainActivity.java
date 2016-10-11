package com.example.ryu.chatclient;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements ChatObserver {

    /*Class member variables*/
    EditText et;
    ListView lv;
    Socket socket = null;
    ChatArrayAdapter adapter;
    String myusername = "";
    String system = "System";
    ServerReader reader;
    ServerWriter writer;

    /*Android System will call this at startup*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Use activity_main layout and portrait orientation on create*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        /*Initialize UI elements*/
        Button b = (Button) findViewById(R.id.button);
        et = (EditText) findViewById(R.id.editText1);
        et.setHint("Type username here");
        lv = (ListView) findViewById(R.id.listView1);

        /*Create ArrayAdapter and pass it to ListView*/
        adapter = new ChatArrayAdapter(getApplicationContext(), R.layout.list_bubble);
        lv.setAdapter(adapter);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("On click listener started." + lv.getAdapter().toString());
                if (socket != null) {
                    String message = (et.getText().toString());
                    /*Don't allow empty message*/
                    if (message.equals("")){
                        return;
                    }

                    /*Get current timeStamp*/
                    String timeStamp = "[" + new SimpleDateFormat("HH.mm").format(Calendar.getInstance().getTime()) + "]";

                    /*Setup username and initialize writer thread only once*/
                    if(myusername == ""){
                        myusername = message;
                        String message2 = myusername + " has joined the chat.";
                        ChatMessage joined = new ChatMessage(timeStamp, system, message2);
                        /*Start ServerWriter thread*/
                        writer = new ServerWriter(socket, joined);
                        /*Give ArrayAdapter the new username*/
                        adapter.setMyName(myusername);
                        /*Show Welcome message with possible commands*/
                        String message3 = "Welcome to the chat. Start by typing something.#Commands:#:history = show history#:userlist = list users#:help = help#:tableflip = (╯°□°）╯︵ ┻━┻#:quit = leave chat";
                        ChatMessage welcome = new ChatMessage(timeStamp, system, message3);
                        adapter.add(welcome); //shows the message in ListView
                        et.setHint("Type here"); //change Hint in EditText
                    }else{
                        /*Send a regular message with timestamp, username and input once username is given*/
                        ChatMessage chatMessage = new ChatMessage(timeStamp, myusername, message);
                        new ServerWriter(socket, chatMessage); //give it to ServerWriter, which sends it to the server.
                    }
                }
                /*Hide keyboard and clear EditText*/
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et.getApplicationWindowToken(), 0);
                et.setText("");

            }
        });
        /*Get ChatHistory instance and register this Activity as an observer in order to display messages*/
        final ChatHistory chatHistory = ChatHistory.getInstance();
        chatHistory.register(this);

        /*Initialize reader*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("10.0.2.2", 53997);//update port manually for now, in production this should be a well defined address with port.
                } catch (IOException e) {
                    e.printStackTrace();//oops
                }
                //Initialize and start reader thread
                reader = new ServerReader(socket, chatHistory);
                Thread thread = new Thread(reader);
                thread.start();

            }

        }).start();
    }

    @Override
    public void update(final ChatMessage message) {
        System.out.print(message.timestamp + message.username + ": " + message.message + "\n>");

        new Thread() {
            public void run() {
                try {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            adapter.add(message);
                            lv.setSelection(lv.getAdapter().getCount()-1);
                        }
                    });

                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }.start();
    }
}