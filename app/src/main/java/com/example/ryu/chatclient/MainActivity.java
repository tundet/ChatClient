package com.example.ryu.chatclient;

import android.content.Context;
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

public class MainActivity extends AppCompatActivity implements ChatObserver {
    EditText et;
    ListView lv;
    Socket socket = null;
    ChatArrayAdapter adapter;
    String myusername = "";
    String system = "System";
    ServerReader reader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = (Button) findViewById(R.id.button);
        et = (EditText) findViewById(R.id.editText1);
        et.setHint("Type username here");
        //tx = (TextView) findViewById(R.id.textView1);
        lv = (ListView) findViewById(R.id.listView1);

        adapter = new ChatArrayAdapter(getApplicationContext(), R.layout.list_bubble);


        lv.setAdapter(adapter);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("On click listener started." + lv.getAdapter().toString());
                if (socket != null) {
                    // If TextView is empty - copy string from EditText
                    //if (tx.getText().toString().equals("")) {
                        String message = (et.getText().toString());
                    if(myusername == ""){
                        myusername = message;
                        String message2 = myusername + " has joined the chat.";
                        ChatMessage joined = new ChatMessage(system, message2);
                        new ServerWriter(socket, joined);
                        adapter.setMyName(myusername);
                        String message3 = "Welcome to the chat. Start by typing something.#Commands:#:history = show history#:userlist = list users#:help = help";
                        ChatMessage welcome = new ChatMessage(system, message3);
                        adapter.add(welcome);
                        et.setHint("Type here");

                    }else{
                        //tx.append(">" + message);
                        ChatMessage chatMessage = new ChatMessage(myusername, message);
                        new ServerWriter(socket, chatMessage);
                    //} else { // Otherwise, clear the TextView.
                      //  tx.append("clear");
                    }
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et.getApplicationWindowToken(), 0);
                et.setText("");

            }
        });

        //create ChatHistory
        final ChatHistory chatHistory = new ChatHistory();
        //register observer
        chatHistory.register(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("10.0.2.2", 53202);
                    System.out.println(socket);
                    System.out.println("Szia");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Hello");
                }
                System.out.println("Create ServerReader");
                //create ServerReader
                reader = new ServerReader(socket, chatHistory);
                Thread thread = new Thread(reader);
                thread.start();

            }

        }).start();
    }

    @Override
    public void update(final ChatMessage message) {
            /*TODO print message to TextView and call runOnUIThread method*/
        System.out.print(message.username + ": " + message.message + "\n>");

        new Thread() {
            public void run() {
                try {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            adapter.add(message);
                            System.out.println(message);
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