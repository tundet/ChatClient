package com.example.ryu.chatclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements ChatObserver {
    EditText et;
    TextView tx;
    Socket socket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = (Button) findViewById(R.id.button);
        et = (EditText) findViewById(R.id.editText1);
        tx = (TextView) findViewById(R.id.textView1);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("On click listener started." + tx.getText().toString());
                if (socket != null) {
                    // If TextView is empty - copy string from EditText
                    //if (tx.getText().toString().equals("")) {
                        String message = (et.getText().toString());
                        String username = "ryu";
                        tx.append(">" + message);
                        ChatMessage chatMessage = new ChatMessage(username, message);
                        new ServerWriter(socket, chatMessage);
                    //} else { // Otherwise, clear the TextView.
                      //  tx.append("clear");
                    //}
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
                    socket = new Socket("10.0.2.2", 51140);
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
                            tx.append("\n" + message.username + ": " + message.message + "\n");
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