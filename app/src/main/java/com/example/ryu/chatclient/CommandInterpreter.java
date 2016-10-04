package com.example.ryu.chatclient;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/*
This is the class for reacting to user's commands and saving the users and messages.

@authors:
1402803 Jämiä Mikko
1406733 Järvinen Otto
1503524 Taba Tünde
 */
public class CommandInterpreter implements Runnable {

    /*Create new scanner object*/
    Scanner reader = new Scanner(System.in);

    /*Constructor for CommandInterpreter*/
    public CommandInterpreter(InputStream inputStream, PrintStream outputStream) {
    }

    /*run() method for CommandInterpreter*/
    public void run() {

        /*Instance variables and objects needed in run() method*/
        String username = "";
        ChatHistory chatHistory = new ChatHistory();
        ChatHistory getHistory = ChatHistory.getInstance();

        ChatAddict newAddict = new ChatAddict("addict");
        chatHistory.register(newAddict);

        //Users userList = new Users();
        Users getUsers = Users.getInstance();

        System.out.println("Hello!");
        System.out.print("Commands:\n:user = Current username\n:history = Show sent messages\n:list = List current users\n:help = List commands\n:quit = Quit application\n");
        System.out.print("Type a command: \n>");

        /*While loop for running the chat application*/
        while (true) {

            /*Read the next command*/
            String command = reader.nextLine();

            /*Switch case for commands*/
            switch (command) {

                /*Case for command :user*/
                case ":user":

                    /*Show current username*/
                    System.out.println("Your current username is "+username);
                    break;

                /*Case for command :quit*/
                case ":quit":

                    /*Quit application*/
                    System.out.println("Goodbye.");
                    System.exit(0);
                    break;

                /*Case for command :history*/
                case ":history":

                    /*Print history*/
                    System.out.print(getHistory.toString() + ">");
                    break;

                /*Case for command :help*/
                case ":help":

                    /*List all commands*/
                    System.out.print("Commands:\n:user = Show username\n:name = Change username\n:history = Show message history\n:list = Show registered users\n:help = List commands\n:quit = Quit application\n>");
                    break;

                /*Case for command :userlist*/
                case ":list":

                    System.out.print(getUsers.toString() + ">");
                    break;

                /*Default case*/
                default:
                    /*Check if username is defined. Set new username if username is not defined.
                    Else add message to chatHistory.*/
                    if (username.isEmpty()) {
                        System.out.println("Username not set.");

                        System.out.print("Type your username: \n>");

                        username = reader.nextLine();

                        getUsers.insert(username);
                        newAddict.changeName(username);

                        System.out.print("Username is " + username + "\n>");
                    } else {

                        ChatMessage chatmessage = new ChatMessage(username, command);

                        chatHistory.insert(chatmessage);

                    }
            }

        }

    }

}
