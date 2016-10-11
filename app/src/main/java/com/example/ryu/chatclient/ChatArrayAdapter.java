package com.example.ryu.chatclient;

/**
 * Created by RYU on 6.10.2016.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatArrayAdapter extends ArrayAdapter<ChatMessage> {

    private TextView countryName;
    private List<ChatMessage> countries = new ArrayList<ChatMessage>();
    private LinearLayout wrapper;
    String myname = "";
    String system = "System";

    @Override
    public void add(ChatMessage object) {
        object.message = object.toString();
        countries.add(object);
        super.add(object);
    }

    public ChatArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public int getCount() {
        return this.countries.size();
    }

    public ChatMessage getItem(int index) {
        return this.countries.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_bubble, parent, false);
        }

        wrapper = (LinearLayout) row.findViewById(R.id.wrapper);

        ChatMessage chatMessage = getItem(position);

        countryName = (TextView) row.findViewById(R.id.comment);
        System.out.println("Adapter side: " + chatMessage.timestamp + "_" + chatMessage.username + "_" + chatMessage.message);

        if(chatMessage.username.equals(system)) {
            countryName.setText(chatMessage.message.replace('#', '\n'));
            countryName.setBackgroundResource(R.drawable.bubble_grey);
            wrapper.setGravity(Gravity.CENTER);
            return row;
        }else{
            countryName.setText(chatMessage.timestamp.replace('.', ':') + chatMessage.username + ": " + chatMessage.message.replace('#', '\n'));

            countryName.setBackgroundResource(chatMessage.username.equals(myname) ? R.drawable.bubble_blue : R.drawable.bubble_white);

            wrapper.setGravity(chatMessage.username.equals(myname) ? Gravity.RIGHT : Gravity.LEFT);

            return row;

        }
    }

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    void setMyName(String myusername){
        this.myname = myusername;
    }

}