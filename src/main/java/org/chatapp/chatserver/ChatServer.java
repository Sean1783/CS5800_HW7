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
    private List<Message> globalMessageRecord = new ArrayList<>();
    private int messageId = 1;


    public Map<Integer, User> getRegisteredUsers() {
        return new HashMap<>(registeredUsers);
    }

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
            Set<User> recipientUsersToSendTo = new HashSet<>();
            for (User recipient : recipients) {
                if (validateRecipient(sender, recipient)) {
                    recipientUsersToSendTo.add(recipient);
                }
            }
            LocalDateTime timestamp = LocalDateTime.now();
            Message message = new Message(messageId, sender, recipientUsersToSendTo, messageContent, timestamp);
            sender.addMessageToHistory(message);
            for (User recipient : recipientUsersToSendTo) {
                recipient.addMessageToHistory(message);
            }
            globalMessageRecord.add(message);
            messageId++;
        }
    }

    public void redo(User sender, Message message) {
        Set<User> recipientUsersToSendTo = message.getRecipients();
        String messageContent = message.getMessageContent();
        sendMessage(sender, recipientUsersToSendTo, messageContent);
    }

    public void revokeMessage(Message message) {
        if (message == null) {
            System.out.println("Message is null");
            return;
        }
        User sender = message.getSender();
        sender.retainLastSent(message);
        sender.deleteMessageFromHistory(message);
        Set<User> recipients = message.getRecipients();
        if (recipients != null) {
            for (User recipient : recipients) {
                recipient.deleteMessageFromHistory(message);
            }
        }
    }

    private boolean validateUsers(User sender,User receiver) {
        return isUserRegistered(sender) && validateRecipient(sender, receiver);
    }

    private boolean isUserRegistered(User user) {
        if(!registeredUsers.containsKey(user.getId())) {
            System.out.println(user + " is not registered.");
            return false;
        }
        return true;
    }

    private boolean validateRecipient(User sender, User receiver) {
        return isUserRegistered(receiver) && !senderIsBlocked(sender, receiver);
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
        for (Message message : globalMessageRecord) {
            System.out.println(message);
        }
    }
}
