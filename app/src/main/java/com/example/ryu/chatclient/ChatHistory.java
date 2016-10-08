package com.example.ryu.chatclient;

import java.util.ArrayList;
/*

@authors
1402803 Jämiä Mikko
1406733 Järvinen Otto
1503524 Taba Tünde
 */
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

    /*insert() method for ChatHistory. Method adds message to chat history and runs notifyObservers method.*/
    public void insert(ChatMessage chatMessage) {
        messageList.add(chatMessage);
        notifyObservers(chatMessage);
    }

    /*toString() method for chatHistory. Method adds time stamp to messages and
    return chatHistory as a string*/
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
