package org.chatapp.chatserver;

import org.chatapp.message.Message;
import org.chatapp.user.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

public class ChatServer {

    private Set<User> registeredUsers = new HashSet<>();
    private HashMap<User, Set<User>> blockedUsers = new HashMap<>();
    private List<Message> messages = new ArrayList<>();

    public void registerUser(User user) {
        registeredUsers.add(user);
    }

    public void unregisterUser(User user) {
        if (registeredUsers.contains(user)) {
            registeredUsers.remove(user);
        } else {
            System.out.println("User does not exist.");
        }
    }

    public void sendMessage(User sender, Set<User> recipients, String messageContent) {
        Set<User> validatedListOfRecipients = new HashSet<>();
        if (isUserRegistered(sender)) {
            // The sender is registered.
            for (User recipient : recipients) {
                if (isUserRegistered(recipient)) {
                    // Receiver is registered.
                    if (!senderIsBlocked(sender, recipient)) {
                        // Sender is not blocked by receiver.
                        validatedListOfRecipients.add(recipient);
                    }
                }
            }
            Message messageToSend =  new Message(sender, validatedListOfRecipients, messageContent);
            messages.add(messageToSend);
        }
    }

    public void receiveMessage(User receiver) {
        System.out.println(receiver.getName() + "'s last message:");
        List<Message> receivedMessages = getAllReceivedMessages(receiver);
        if (!receivedMessages.isEmpty()) {
            Message lastMessage = messages.get(receivedMessages.size() - 1);
            System.out.println("From : " + lastMessage.getSender());
            System.out.println("To : " + receiver);
            System.out.println("Message : " + lastMessage.getMessageContent());
            System.out.println("Timestamp : " + lastMessage.getDate());
        }
    }

    public void undoLastMessage(User sender) {
        List<Message> sentMessages = getAllSentMessages(sender);
        if (!sentMessages.isEmpty()) {
            Message lastMessage = sentMessages.get(sentMessages.size() - 1);
            System.out.println("Attempting to remove: " + lastMessage);
            boolean removed = messages.remove(lastMessage);
            if (removed) {
                System.out.println("Removed: " + lastMessage);
            } else {
                System.out.println("Message not found in messages list for removal: " + lastMessage);
            }
        }
    }

    private List<Message> getAllReceivedMessages(User receiver) {
        List<Message> receivedMessages = new ArrayList<>();
        for (Message message : messages) {
            Set<User> recipients = message.getRecipients();
            if (recipients.contains(receiver)) {
                receivedMessages.add(message);
            }
        }
        return receivedMessages;
    }

    private List<Message> getAllSentMessages(User sender) {
        List<Message> sentMessages = new ArrayList<>();
        for (Message message : messages) {
            if (message.getSender().equals(sender)) {
                sentMessages.add(message);
            }
        }
        return sentMessages;
    }

    private boolean validateUsers(User sender,User receiver) {
        return isUserRegistered(sender) && isUserRegistered(receiver) && !senderIsBlocked(sender, receiver);
    }

    private boolean isUserRegistered(User user) {
        if(!registeredUsers.contains(user)) {
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
