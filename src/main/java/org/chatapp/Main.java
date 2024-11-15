package org.chatapp;

import org.chatapp.chatserver.ChatServer;
import org.chatapp.user.User;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        // Create the server and users.
        ChatServer server = new ChatServer();
        User userOne = new User("Sean", server);
        User userTwo = new User("Katie", server);
        User userThree = new User("Dottie", server);
        server.registerUser(userOne);
        server.registerUser(userTwo);
        server.registerUser(userThree);

        // Create the message details.
        Set<User> recipientList = new HashSet<>();
        recipientList.add(userTwo);
        recipientList.add(userThree);
        String messageContent = "I love you!";

        // Send a message from userOne
        userOne.sendMessage(messageContent, recipientList);
        userTwo.receiveMessage();
        userThree.receiveMessage();

        String kittyMessage = "You're the best kitty!";
        recipientList.remove(userTwo);
        userOne.sendMessage(kittyMessage, recipientList);
        userOne.undoLastMessage();

        userTwo.receiveMessage();
        userThree.receiveMessage();

    }
}