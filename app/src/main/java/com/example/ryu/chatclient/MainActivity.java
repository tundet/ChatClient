package com.example.ryu.chatclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    String username = "";
    String system = "System";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = (Button) findViewById(R.id.button);
        et = (EditText) findViewById(R.id.editText1);
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
                    if(username == ""){
                        username = message;
                        String message2 = "Username set to " + username;
                        ChatMessage chatMessage = new ChatMessage(true, system, message2);
                        new ServerWriter(socket, chatMessage);
                    }else{
                        //tx.append(">" + message);
                        ChatMessage chatMessage = new ChatMessage(false, username, message);
                        new ServerWriter(socket, chatMessage);
                    //} else { // Otherwise, clear the TextView.
                      //  tx.append("clear");
                    }
                }
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
                    socket = new Socket("10.0.2.2", 49765);
                    System.out.println(socket);
                    System.out.println("Szia");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Hello");
                }
                System.out.println("Create ServerReader");
                //create ServerReader
                ServerReader reader = new ServerReader(socket, chatHistory);
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