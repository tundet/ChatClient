package com.example.ryu.chatclient;

/**
 * Created by RYU on 4.10.2016.
 */
public interface ObservableChat {

    /*register() method for registering new observer*/
    public void register(ChatObserver observer);

    /*deregister() method for removing observer*/
    public void deregister(ChatObserver observer);

    /*notifyObservers() method for notifying observers*/
    void notifyObservers(ChatMessage message);
}
