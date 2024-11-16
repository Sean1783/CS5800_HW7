package org.chatapp.user;

import org.chatapp.chathistory.ChatHistory;
import org.chatapp.chatserver.ChatServer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class User {

    private ChatHistory history;
    private final int id;
    private String name;
    private List<User> blockedUsers;
    private static int counter = 0;

    public User(String name) {
        history = new ChatHistory();
        this.id = counter++;
        this.name = name;
        blockedUsers = new ArrayList<User>();
    }

    public void register(ChatServer server) {
        server.registerUser(this);
    }

    public void unregister(ChatServer server) {
        server.unregisterUser(this);
    }

    public void attemptMessageSend(String messageContent, Set<User> recipients, ChatServer server) {
        server.sendMessage(this, recipients, messageContent);
    }

    public void sendMessage(int messageId, int senderId, String senderName, int receiverId, String receiverName, String messageContent, LocalDateTime timestamp) {
        history.addSentMessage(messageId, senderId, senderName, receiverId, receiverName, messageContent, timestamp);
    }

    public void receiveMessage(int messageId, int senderId, String senderName, int receiverId, String receiverName, String messageContent, LocalDateTime timestamp) {
        history.addReceivedMessage(messageId, senderId, senderName, receiverId, receiverName, messageContent, timestamp);
    }

    public void viewChatHistory(User chatPartner) {
        List<String> chatHistory = history.getChatHistoryWithUser(chatPartner);
        if (chatHistory.isEmpty()) {
            System.out.println("No chat history found.");
        }
        for (String s : chatHistory) {
            System.out.println(s);
        }
    }

    public void undoLastMessage(ChatServer server) {
        int poppedMessageId = history.undoLastMessage();
        server.revokeMessage(poppedMessageId);
    }

    public void removeReceivedMessage(int messageId) {
        history.revokeMessage(messageId);
        System.out.println("Message " + messageId + " revoked");
    }

    // This needs to be figured out. How to get all the data from history to User to call attemptSendMessage.
    public void redoLastMessage() {
        history.redoLastMessage();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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
    public String toString() {
        return "User [id=" + id + ", name=" + name + "]";
    }
}
