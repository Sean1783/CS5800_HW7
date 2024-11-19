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

    private List<User> registeredUsers = new ArrayList<>();
    private Map<User, Set<User>> blockedUsers = new HashMap<>();
    private List<Message> globalMessageRecord = new ArrayList<>();

    public List<Message> getGlobalMessageRecord() {
        return new ArrayList<>(globalMessageRecord);
    }

    public Map<User, Set<User>> getBlockedUsers() {
        return new HashMap<>(blockedUsers);
    }

    public List<User> getRegisteredUsers() {
        return new ArrayList<>(registeredUsers);
    }

    public void registerUser(User user) {
        registeredUsers.add(user);
    }

    public void unregisterUser(User user) {
        if (!registeredUsers.remove(user)) {
            System.out.println("User does not exist.");
        }
    }

    public void sendMessage(User sender, Set<User> recipients, String messageContent) {
        if (isUserRegistered(sender)) {
            Set<User> recipientUsersToSendTo = createValidatedRecipientList(sender, recipients);
            LocalDateTime timestamp = LocalDateTime.now();
            Message message = new Message(sender, recipientUsersToSendTo, messageContent, timestamp);
            sender.addMessageToHistory(message);
            for (User recipient : recipientUsersToSendTo) {
                recipient.addMessageToHistory(message);
            }
            globalMessageRecord.add(message);
        } else {
            System.out.println("User is not registered.");
        }
    }

    private Set<User> createValidatedRecipientList(User sender, Set<User> recipients) {
        Set<User> validRecipients = new HashSet<>();
        for (User recipient : recipients) {
            if (validateRecipient(sender, recipient)) {
                validRecipients.add(recipient);
            }
        }
        return validRecipients;
    }

    public void redo(User sender, Message message) {
        if (message == null) {
            System.out.println("Message is null");
            throw new IllegalArgumentException();
        }
        Set<User> recipientUsersToSendTo = message.getRecipients();
        String messageContent = message.getMessageContent();
        sendMessage(sender, recipientUsersToSendTo, messageContent);
    }

    public void revokeMessage(Message message) {
        if (message == null) {
            System.out.println("Message is null");
            throw new IllegalArgumentException();
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

    public boolean validateUsers(User sender,User receiver) {
        return isUserRegistered(sender) && validateRecipient(sender, receiver);
    }

    public boolean isUserRegistered(User user) {
        if (registeredUsers.contains(user)) {
            return true;
        }
        System.out.println(user + " is not registered.");
        return false;
    }

    public boolean senderIsBlocked(User sender, User receiver) {
        Set<User> blockedSenders = blockedUsers.get(receiver);
        if (blockedSenders != null) {
            return blockedSenders.contains(sender);
        }
        return false;
    }

    public boolean validateRecipient(User sender, User receiver) {
        return isUserRegistered(receiver) && !senderIsBlocked(sender, receiver);
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
