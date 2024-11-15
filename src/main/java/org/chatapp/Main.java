package org.chatapp;

import org.chatapp.chatserver.ChatServer;
import org.chatapp.user.User;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        // Create the server and users.
        ChatServer server = new ChatServer();
        User sean = new User("Sean", server);
        User katie = new User("Katie", server);
        User kitty = new User("Dottie", server);

        sean.register();
        katie.register();
        kitty.register();

        // Create the message details.
        Set<User> recipientList = new HashSet<>();
        recipientList.add(katie);
        recipientList.add(kitty);
        String messageContent = "I love you!";

        // Send a message from userOne
        sean.sendMessage(messageContent, recipientList);
//        userTwo.receiveMessagesFromUser(userOne);
//        userThree.receiveMessagesFromUser(userOne);

        String kittyMessage = "You're the best kitty!";
        recipientList.remove(katie);
        sean.sendMessage(kittyMessage, recipientList);

        String kittyDadMessage = "You're the best dad!";
        recipientList.clear();
        recipientList.add(sean);
        kitty.sendMessage(kittyDadMessage, recipientList);

//        userTwo.receiveMessagesFromUser(userOne);
//        userThree.receiveMessagesFromUser(userOne);
        sean.viewChatHistory(kitty);
        kitty.viewChatHistory(sean);

    }
}