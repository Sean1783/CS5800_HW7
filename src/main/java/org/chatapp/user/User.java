package org.chatapp.user;

import org.chatapp.chathistory.ChatHistory;
import org.chatapp.chathistory.IterableByUser;
import org.chatapp.chatserver.ChatServer;
import org.chatapp.message.Message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class User implements IterableByUser {

    private final ChatHistory chatHistory;
    private final int id;
    private final String name;
    private List<User> blockedUsers;
    private static int counter = 0;

    public User(String name) {
        chatHistory = new ChatHistory();
        this.id = counter++;
        this.name = name;
        blockedUsers = new ArrayList<User>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void register(ChatServer server) {
        server.registerUser(this);
    }

    public void unregister(ChatServer server) {
        server.unregisterUser(this);
    }

    public void sendMessage(String messageContent, Set<User> recipients, ChatServer server) {
        server.sendMessage(this, recipients, messageContent);
    }

    public void addMessageToSentHistory(Message message) {
        chatHistory.addSentMessage(message);
    }

    public void addMessageToReceivedHistory(Message message) {
        chatHistory.addReceivedMessage(message);
    }

    public void removeReceivedMessage(Message message) {
        boolean removalWasSuccessful = chatHistory.revokeMessage(message);
        if (removalWasSuccessful) {
            System.out.println("Message removed.");
        } else {
            System.out.println("Message remove failed.");
        }
    }

    public void undoLastMessage(ChatServer server) {
        server.revokeMessage(chatHistory.undoLastMessage());
    }

    public void redoLastMessage(ChatServer server) {
        Message lastMessage = chatHistory.getPoppedMessage();
        server.redo(this, lastMessage);
    }

    public void blockUser(User userToBlock, ChatServer server) {
        blockedUsers.add(userToBlock);
        server.blockUsers(this, userToBlock);
    }

    public void unblockUser(User userToUnblock, ChatServer server) {
        blockedUsers.remove(userToUnblock);
        server.unblockUsers(this, userToUnblock);
    }

    @Override
    public Iterator<Message> iterator(User userToSearchWith) {
        return chatHistory.iterator(userToSearchWith);
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + "]";
    }
}
