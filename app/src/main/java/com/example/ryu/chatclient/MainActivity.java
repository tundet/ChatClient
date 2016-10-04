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

public class MainActivity extends AppCompatActivity {
    EditText et;
    TextView tx;

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
                // If TextView is empty - copy string from EditText
                if (tx.getText().toString().equals("")) {
                    tx.setText(et.getText().toString());
                } else { // Otherwise, clear the TextView.
                    tx.setText("");
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket = null;
                try {
                    socket = new Socket("10.0.2.2", 63779);
                    System.out.println(socket);
                    System.out.println("Szia");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Hello");
                }

                ServerReader reader = new ServerReader(socket);

                Thread thread = new Thread(reader);
                thread.start();
            }

        }).start();
    }
}