package com.example.ryu.chatclient;

/*
This class is for printing the new messages in the chat.
@authors
1402803 Jämiä Mikko
1406733 Järvinen Otto
1503524 Taba Tünde
 */
public class ChatAddict implements ChatObserver {
    /*Instance variable for ChatAddict*/
    String name;

    /*Constructor for ChatAddict class*/
    public ChatAddict(String name) {
        this.name = name;
    }

    /*Changes the current name to new one*/
    public void changeName(String newName) {
        this.name = newName;
    }

    /*Prints the message user has typed*/
    @Override
    public void update(ChatMessage message) {
        System.out.print(name + ": " + message + "\n>");
    }

}
