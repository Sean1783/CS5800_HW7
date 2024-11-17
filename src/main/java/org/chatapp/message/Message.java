package org.chatapp.message;

import org.chatapp.messagememento.MessageMemento;

import java.time.LocalDateTime;
import java.util.Set;

public class Message {
    private int messageId;
    private Endpoint sender;
    private Set<Endpoint> recipients;
    private String messageContent;
    private LocalDateTime timestamp;

    public Message(int messageId, Endpoint sender, Set<Endpoint> receivers, String messageContent, LocalDateTime timestamp) {
        this.messageId = messageId;
        this.sender = sender;
        this.recipients = receivers;
        this.messageContent = messageContent;
        this.timestamp = timestamp;
    }

    public static class Endpoint {
        String name;
        int id;

        public Endpoint(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return "{id=" + id + ", name=" + name + "}";
        }
    }

    public Endpoint getSender() {
        return sender;
    }

    public Set<Endpoint> getRecipients() {
        return recipients;
    }

    public MessageMemento save() {
        return new MessageMemento(this);

    }

    public void restore(MessageMemento memento) {
        this.messageId = memento.getMessageId();
        this.sender = memento.getSender();
        this.recipients = memento.getRecipients();
        this.messageContent = memento.getMessageContent();
        this.timestamp = memento.getTimestamp();
    }

    public Integer getMessageId() {
        return messageId;
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
                "messageId=" + messageId +
                ", sender=" + sender +
                ", recipients=" + recipients +
                ", messageContent=" + messageContent +
                ", date=" + timestamp + '}';
    }
}
