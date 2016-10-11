package com.example.ryu.chatclient;

/**
 * This class is an ArrayAdapter that we use to update the ListView in the UI with the new messages.
 * @authors
 * Group Tableflipz
 * 1402803 Jämiä Mikko
 * 1406733 Järvinen Otto
 * 1503524 Taba Tünde
 */

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatArrayAdapter extends ArrayAdapter<ChatMessage> {

    /*Class member variables*/
    private TextView newMessage;
    private List<ChatMessage> messages = new ArrayList<ChatMessage>();
    private LinearLayout wrapper;
    String myname = "";
    String system = "System";

    /*Constructor with application context and layout to use*/
    public ChatArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    /*Method for adding a message to messages list*/
    @Override
    public void add(ChatMessage object) {
        object.message = object.toString();
        messages.add(object);
        super.add(object);
    }

    /*Method for getting the size of messages list*/
    public int getCount() {

        return this.messages.size();
    }

    /*Method for getting specific chat message*/
    public ChatMessage getItem(int index) {

        return this.messages.get(index);
    }

    /*Method for giving the adapter the user's username*/
    void setMyName(String myusername){
        this.myname = myusername;
    }

    /*Method for inflating layout resources aka listing chat bubbles in this context*/
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_bubble, parent, false);
        }

        /*Initialize wrapper*/
        wrapper = (LinearLayout) row.findViewById(R.id.wrapper);

        /*Initialize new ChatMessage*/
        ChatMessage chatMessage = getItem(position);

        /*Initialize new message's textView*/
        newMessage = (TextView) row.findViewById(R.id.comment);

        /*If message sent by System show grey bubble in center
        * if message sent by user show blue bubble on right
        * if message sent by someone else show white bubble on left*/
        if(chatMessage.username.equals(system)) {
            /*Since we are reading lines only a new line as been replaced with #,
            thus it needs to be turned back to new line*/
            newMessage.setText(chatMessage.message.replace('#', '\n'));
            newMessage.setBackgroundResource(R.drawable.bubble_grey);
            wrapper.setGravity(Gravity.CENTER);
            return row;
        }else{
            /*Change the way timeStamp is printed out so it is HH:mm instead of HH.mm
            and change # into a new line*/
            newMessage.setText(chatMessage.timestamp.replace('.', ':') + chatMessage.username + ": " + chatMessage.message.replace('#', '\n'));
            newMessage.setBackgroundResource(chatMessage.username.equals(myname) ? R.drawable.bubble_blue : R.drawable.bubble_white);
            wrapper.setGravity(chatMessage.username.equals(myname) ? Gravity.RIGHT : Gravity.LEFT);
            return row;

        }
    }

}