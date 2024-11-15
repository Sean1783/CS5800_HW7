package org.chatapp.message;

import org.chatapp.user.User;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Message {
    private final User sender;
    private Set<User> recipients = new HashSet<>();
    private final String messageContent;
    private final LocalDateTime date;

    public Message(User sender, Set<User> recipients, String messageContent) {
        this.sender = sender;
        this.recipients = recipients;
        this.messageContent = messageContent;
        this.date = LocalDateTime.now();
    }

    public User getSender() {
        return sender;
    }

    public Set<User> getRecipients() {
        return recipients;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Message{" + "sender=" + sender +
                ", recipients=" + recipients +
                ", messageContent=" + messageContent +
                ", date=" + date + '}';
    }
}
