package com.example.ryu.chatclient;

/**
 * This class is a singleton that contains an arraylist with all the messages gotten and works as
 * an observable subject for observers.
 * @authors
 * Group Tableflipz
 * 1402803 Jämiä Mikko
 * 1406733 Järvinen Otto
 * 1503524 Taba Tünde
 */

import java.util.ArrayList;

public class ChatHistory implements ObservableChat {

    /*Instance variables for ChatHistory*/
    private ArrayList<ChatObserver> observers;
    static ArrayList<ChatMessage> messageList;
    private static ChatHistory instance = new ChatHistory();


    /*Constructor for ChatHistory*/
    private ChatHistory() {
        this.observers = new ArrayList<>();
        this.messageList = new ArrayList<>();
    }

    /*getInstance() method for ChatHistory. Method returns the instance,
    for printing the chat history*/
    public static ChatHistory getInstance() {
        return instance;
    }

    /*insert() method for ChatHistory. Method adds message to chat history and thus
    * notifies all the observers*/
    public void insert(ChatMessage chatMessage) {
        messageList.add(chatMessage);
        notifyObservers(chatMessage);
    }

    /*toString() method for showing the History*/
    @Override
    public String toString() {
        String list = new String();

        for (ChatMessage element : messageList) {

            list += (element.username + ": " + element.message + "\n");
        }

        return list;
    }

    /*register() method for adding new observers*/
    @Override
    public void register(ChatObserver observer) {
        observers.add(observer);
    }

    /*deregister() method for removing observers*/
    @Override
    public void deregister(ChatObserver observer) {
        observers.remove(observer);
    }

    /*notifyObservers() notify observers for changes*/
    @Override
    public void notifyObservers(ChatMessage message) {
        for (ChatObserver observer : observers) {
            observer.update(message);
        }
    }
}
