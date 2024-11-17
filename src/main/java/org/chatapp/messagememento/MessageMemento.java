package org.chatapp.messagememento;

import org.chatapp.message.Message;

import java.time.LocalDateTime;
import java.util.Set;

public class MessageMemento {

    private final int messageId;
    private final Message.Endpoint sender;
    private final Set<Message.Endpoint> recipients;
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

    public Message.Endpoint getSender() {
        return sender;
    }

    public Set<Message.Endpoint> getRecipients() {
        return recipients;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
