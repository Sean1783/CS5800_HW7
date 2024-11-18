package org.chatapp.messagememento;

import org.chatapp.message.Message;
import org.chatapp.user.User;

import java.time.LocalDateTime;
import java.util.Set;

public class MessageMemento {

    private final int messageId;
    private final User sender;
    private final Set<User> recipients;
    private final String messageContent;
    private final LocalDateTime timestamp;

    public MessageMemento(Message message) {
        this.messageId = message.getMessageId();
        this.sender = message.getSender();
        this.recipients = message.getRecipients();
        this.messageContent = message.getMessageContent();
        this.timestamp = message.getTimestamp();
    }

    public int getMessageId() {
        return messageId;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
