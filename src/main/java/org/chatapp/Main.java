package org.chatapp;

import org.chatapp.chatserver.ChatServer;
import org.chatapp.user.User;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        // Create the server and users.
        ChatServer server = new ChatServer();
        User sean = new User("Sean");
        User katie = new User("Katie");
        User kitty = new User("Dottie");

        sean.register(server);
        katie.register(server);
        kitty.register(server);

        // Create the message details.
        Set<User> recipientList = new HashSet<>();
        recipientList.add(kitty);
        recipientList.add(katie);
        String messageContent = "I love you!";

        // Send a message from userOne
        sean.attemptMessageSend(messageContent, recipientList, server);
        kitty.viewChatHistory(sean);
        katie.viewChatHistory(sean);
        System.out.println("\n");
        sean.undoLastMessage(server);
        kitty.viewChatHistory(sean);
        katie.viewChatHistory(sean);
        System.out.println("\n");
        sean.redoLastMessage(server);
        kitty.viewChatHistory(sean);
        katie.viewChatHistory(sean);

    }
}