package com.example.ryu.chatclient;

/**
 * This interface initializes the update method for an observer
 * @authors
 * Group Tableflipz
 * 1402803 Jämiä Mikko
 * 1406733 Järvinen Otto
 * 1503524 Taba Tünde
 */
public interface ChatObserver {

    /*update() method for ChatObserver interface*/
    public void update(ChatMessage message);
}
