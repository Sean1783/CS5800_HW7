package org.chatapp.user;

import org.chatapp.chatserver.ChatServer;

import java.util.Set;

public class User {

    private final ChatServer server;

    private final Integer id;
    private String name;
    private static Integer counter = 0;

    public User(String name, ChatServer server) {
        this.server = server;
        this.id = counter++;
        this.name = name;
    }

    public void sendMessage(String messageContent, Set<User> recipients) {
        server.sendMessage(this, recipients, messageContent);
    }

    public void receiveMessage() {
        server.receiveMessage(this);
    }

    public void undoLastMessage() {
        server.undoLastMessage(this);
    }

    public Integer getId() {
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
