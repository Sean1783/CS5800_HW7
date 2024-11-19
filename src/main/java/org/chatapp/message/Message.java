package org.chatapp.message;

import org.chatapp.messagememento.MessageMemento;
import org.chatapp.user.User;

import java.time.LocalDateTime;
import java.util.Set;

public class Message {
    private User sender;
    private Set<User> recipients;
    private String messageContent;
    private LocalDateTime timestamp;

    public Message() {}

    public Message(User sender, Set<User> receivers, String messageContent, LocalDateTime timestamp) {
        this.sender = sender;
        this.recipients = receivers;
        this.messageContent = messageContent;
        this.timestamp = timestamp;
    }

    public User getSender() {
        return sender;
    }

    public Set<User> getRecipients() {
        return recipients;
    }

    public MessageMemento save() {
        return new MessageMemento(this);
    }

    public void restore(MessageMemento memento) {
        this.sender = memento.getSender();
        this.recipients = memento.getRecipients();
        this.messageContent = memento.getMessageContent();
        this.timestamp = memento.getTimestamp();
    }

    public String getMessageContent() {
        return messageContent;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                ", sender=" + sender +
                ", recipients=" + recipients +
                ", messageContent=\"" + messageContent + "\"" +
                ", date=" + timestamp + '}';
    }
}
