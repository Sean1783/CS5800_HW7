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
            Message.Endpoint sendingEndpoint = convertUserToEndpoint(sender);
            Set<Message.Endpoint> recipientEndpoints = new HashSet<>();
            Set<User> recipientUsersToSendTo = new HashSet<>();
            for (User recipient : recipients) {
                if (validateRecipient(sender, recipient)) {
                    Message.Endpoint receivingParty = convertUserToEndpoint(recipient);
                    recipientEndpoints.add(receivingParty);
                    recipientUsersToSendTo.add(recipient);
                }
            }
            LocalDateTime timestamp = LocalDateTime.now();
            Message message = new Message(messageId, sendingEndpoint, recipientEndpoints, messageContent, timestamp);
            sender.addMessageToSentHistory(message);
            for (User recipient : recipientUsersToSendTo) {
                recipient.addMessageToReceivedHistory(message);
            }
            globalMessageRecord.add(message);
            globalMessageIdToRecipientMap.put(messageId, recipientUsersToSendTo);
            messageId++;
        }
    }

    private Message.Endpoint convertUserToEndpoint(User user) {
        String senderName = user.getName();
        int userId = user.getId();
        return new Message.Endpoint(senderName, userId);
    }

    public void redo(User sender, Message message) {
        int messageId = message.getMessageId();
        Set<User> recipientUsersToSendTo = globalMessageIdToRecipientMap.get(messageId);
        String messageContent = message.getMessageContent();
        sendMessage(sender, recipientUsersToSendTo, messageContent);
    }

    public void revokeMessage(Message message) {
        if (message == null) {
            System.out.println("Message is null");
            return;
        }
        int messageId = message.getMessageId();
        Set<User> recipients = globalMessageIdToRecipientMap.get(messageId);
        if (recipients != null) {
            for (User recipient : recipients) {
                recipient.removeReceivedMessage(message);
            }
        }
    }

    private boolean validateRecipient(User sender, User receiver) {
        return isUserRegistered(receiver) && !senderIsBlocked(sender, receiver);
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
