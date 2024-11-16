package org.chatapp.chatserver;

import org.chatapp.message.Message;
import org.chatapp.user.User;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

public class ChatServer {

    private Map<Integer, User> registeredUsers = new HashMap<>();
    private Map<User, Set<User>> blockedUsers = new HashMap<>();
    private List<Message> messages = new ArrayList<>();
    private Map<Integer, Set<User>> globalMessageIdToRecipientMap = new HashMap<>();
    private int messageId = 1;

    public void registerUser(User user) {
        registeredUsers.put(user.getId(), user);
    }

    public void unregisterUser(User user) {
        if (registeredUsers.containsKey(user.getId())) {
            registeredUsers.remove(user.getId());
        } else {
            System.out.println("User does not exist.");
        }
    }

    public void sendMessage(User sender, Set<User> recipients, String messageContent) {
        if (isUserRegistered(sender)) {
            Set<User> recipientSet = new HashSet<>();
            for (User recipient : recipients) {
                if (validateRecipient(sender, recipient)) {
                    int senderId = sender.getId();
                    String senderName = sender.getName();
                    int receiverId = recipient.getId();
                    String receiverName = recipient.getName();
                    LocalDateTime timestamp = LocalDateTime.now();
                    recipientSet.add(recipient);
                    sender.sendMessage(messageId, senderId,senderName, receiverId, receiverName, messageContent, timestamp);
                    recipient.receiveMessage(messageId, senderId,senderName, receiverId, receiverName, messageContent, timestamp);
                    messages.add(new Message(messageId, senderId,senderName, receiverId, receiverName, messageContent, timestamp));
                }
            }

            globalMessageIdToRecipientMap.put(messageId, recipientSet);
            messageId++;
        }
    }

    public void revokeMessage(int messageId) {
        Set<User> recipients = globalMessageIdToRecipientMap.get(messageId);
        if (recipients != null) {
            for (User recipient : recipients) {
                recipient.removeReceivedMessage(messageId);
            }
        }
    }

    public void undoLastMessage(User sender) {
//        List<Message> sentMessages = getAllSentMessages(sender);
//        if (!sentMessages.isEmpty()) {
//            Message lastMessage = sentMessages.get(sentMessages.size() - 1);
//            System.out.println("Attempting to remove: " + lastMessage);
//            boolean removed = messages.remove(lastMessage);
//            if (removed) {
//                System.out.println("Removed: " + lastMessage);
//                // Remove messages from relevant other user's history.
//            } else {
//                System.out.println("Message not found in messages list for removal: " + lastMessage);
//            }
//        }
    }


    private List<Message> getAllSentMessages(User sender) {
        List<Message> sentMessages = new ArrayList<>();
        return sentMessages;
    }

    private boolean validateRecipient(User sender, User receiver) {
        return isUserRegistered(receiver) && !senderIsBlocked(sender, receiver);
    }

    private boolean validateUsers(User sender,User receiver) {
        return isUserRegistered(sender) && isUserRegistered(receiver) && !senderIsBlocked(sender, receiver);
    }

    private boolean isUserRegistered(User user) {
        if(!registeredUsers.containsKey(user.getId())) {
            System.out.println(user + " is not registered.");
            return false;
        }
        return true;
    }

    private boolean senderIsBlocked(User sender, User receiver) {
        Set<User> blockedSenders = blockedUsers.get(receiver);
        if (blockedSenders != null) {
            return blockedSenders.contains(sender);
        }
        return false;
    }

    public void blockUsers(User blocker, User blocked) {
        if (validateUsers(blocker, blocked)) {
            if (!blockedUsers.containsKey(blocker)) {
                Set<User> blockedSenders = new HashSet<>();
                blockedSenders.add(blocked);
                blockedUsers.put(blocker, blockedSenders);
            } else {
                blockedUsers.get(blocker).add(blocked);
            }
        }
    }

    public void unblockUsers(User blocker, User blocked) {
        if (validateUsers(blocker, blocked)) {
            if (blockedUsers.containsKey(blocker)) {
                blockedUsers.get(blocker).remove(blocked);
            }
        }
    }

    public void dumpMessages() {
        for (Message message : messages) {
            System.out.println(message);
        }
    }
}
