package org.chatapp.user;

import org.chatapp.chathistory.ChatHistory;
import org.chatapp.chatserver.ChatServer;

import java.util.List;
import java.util.Set;

public class User {

    private final ChatServer server;
    private ChatHistory history;
    private final int id;
    private String name;
    private static int counter = 0;

    public User(String name, ChatServer server) {
        this.server = server;
        history = new ChatHistory(counter);
        this.id = counter++;
        this.name = name;
    }

    public void register() {
        server.registerUser(this);
    }

    public void unregister() {
        server.unregisterUser(this);
    }

    public void sendMessage(String messageContent, Set<User> recipients) {
        server.sendMessage(this, recipients, messageContent);
    }

    public void viewChatHistory(User chatPartner) {
        List<String> chatHistory = history.getChatHistoryWithUser(chatPartner);
        for (String s : chatHistory) {
            System.out.println(s);
        }
    }

    public void receiveMessagesFromUser(User userToReceiveFrom) {
        List<String> messageHistory = history.receiveMessagesFromUser(userToReceiveFrom);
        for (String message : messageHistory) {
            System.out.println(message);
        }
    }

    public ChatHistory getHistory() {
        return history;
    }

    public void undoLastMessage() {
        server.undoLastMessage(this);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + "]";
    }
}
