package org.chatapp;

import org.chatapp.chatserver.ChatServer;
import org.chatapp.message.Message;
import org.chatapp.user.User;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        ChatServer server = new ChatServer();
        User sean = new User("Sean");
        User katie = new User("Katie");
        User kitty = new User("Dottie");

        sean.register(server);
        katie.register(server);
        kitty.register(server);

        Set<User> recipientList = new HashSet<>();
        recipientList.add(kitty);
        recipientList.add(katie);
        String messageContent = "You're the best!";

        // Your program should include a driver that demonstrates these features with 3 users.

        // Users can send messages to one or more other users through the chat server.
        // sean --> kitty, katie
        sean.sendMessage(messageContent, recipientList, server);
        System.out.println("Kitty's chat history with Sean.");
        // Users can receive messages from other users and view the chat history for a specific user.
        Iterator<Message> kittyIterator = kitty.iterator(sean);
        while (kittyIterator.hasNext()) {
            Message message = kittyIterator.next();
            System.out.println(message);
        }
        System.out.println("\n");

        System.out.println("Katie's chat history with Sean.");
        Iterator<Message> katieIterator = katie.iterator(sean);
        while (katieIterator.hasNext()) {
            Message message = katieIterator.next();
            System.out.println(message);
        }
        System.out.println("\n");

        recipientList.clear();
        recipientList.add(sean);
        messageContent = "What's for dinner?";
        // katie --> sean
        katie.sendMessage(messageContent, recipientList, server);

        System.out.println("Sean's chat history with Katie after receiving her message.");
        Iterator<Message> seanIterator = sean.iterator(katie);
        while (seanIterator.hasNext()) {
            Message message = seanIterator.next();
            System.out.println(message);
        }
        System.out.println("\n");

        messageContent = "Whatever you're making.";
        recipientList.clear();
        recipientList.add(katie);
        // sean --> katie
        sean.sendMessage(messageContent, recipientList, server);
        System.out.println("Sean's chat history with Katie following his response.");
        seanIterator = sean.iterator(katie);
        while (seanIterator.hasNext()) {
            Message message = seanIterator.next();
            System.out.println(message);
        }
        System.out.println("\n");

        // Users can undo the last message they sent using the Memento design pattern.
        // sean <--undo--> katie
        sean.undoLastMessage(server);
        System.out.println("Katie's chat history with Sean after he undid his last response.");
        katieIterator = katie.iterator(sean);
        while (katieIterator.hasNext()) {
            Message message = katieIterator.next();
            System.out.println(message);
        }
        System.out.println("\n");

        sean.redoLastMessage(server);
        System.out.println("Katie's chat history with Sean after he redid his last response.");
        katieIterator = katie.iterator(sean);
        while (katieIterator.hasNext()) {
            Message message = katieIterator.next();
            System.out.println(message);
        }
        System.out.println("\n");

        System.out.println("Kitty's unaffected chat history with Sean after he undid his last sent message.");
        kittyIterator = kitty.iterator(sean);
        while (kittyIterator.hasNext()) {
            Message message = kittyIterator.next();
            System.out.println(message);
        }
        System.out.println("\n");

        // Users can block messages from specific users using the Mediator design pattern.
        System.out.println("Katie blocks Sean.");
        // sean ---X katie
        katie.blockUser(sean, server);
        messageContent = "How about pizza?";
        recipientList.clear();
        recipientList.add(katie);
        sean.sendMessage(messageContent, recipientList, server);

        System.out.println("Katie's chat history with Sean after blocking him. His last message to her was blocked.");
        katieIterator = katie.iterator(sean);
        while (katieIterator.hasNext()) {
            Message message = katieIterator.next();
            System.out.println(message);
        }
        System.out.println("\n");

        messageContent = "Does anybody want pizza?";
        recipientList.clear();
        recipientList.add(katie);
        recipientList.add(kitty);
        // sean --> (x)katie, kitty
        sean.sendMessage(messageContent, recipientList, server);

        System.out.println("Kitty's unaffected chat history with Sean after his last sent message.");
        kittyIterator = kitty.iterator(sean);
        while (kittyIterator.hasNext()) {
            Message message = kittyIterator.next();
            System.out.println(message);
        }
        System.out.println("\n");

        recipientList.clear();
        recipientList.add(katie);
        recipientList.add(sean);
        messageContent = "With anchovies please.";
        // kitty --> katie, sean
        kitty.sendMessage(messageContent, recipientList, server);

        System.out.println("Katie's chat history with Kitty.");
        katieIterator = katie.iterator(kitty);
        while (katieIterator.hasNext()) {
            Message message = katieIterator.next();
            System.out.println(message);
        }
        System.out.println("\n");

        System.out.println("Sean's chat history with Kitty.");
        seanIterator = sean.iterator(kitty);
        while (seanIterator.hasNext()) {
            Message message = seanIterator.next();
            System.out.println(message);
        }
        System.out.println("\n");

    }
}