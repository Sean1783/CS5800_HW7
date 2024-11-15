package org.chatapp.message;

import org.chatapp.messagememento.MessageMemento;

import java.time.LocalDateTime;

public class Message {
    private int messageId;
    private int senderId;
    private String senderName;
    private int receiverId;
    private String receiverName;
    private String messageContent;
    private LocalDateTime timestamp;
//    private static int counter = 0;

    public Message(int messageId, int senderId, String senderName, int recipientId, String receiverName, String messageContent, LocalDateTime timestamp) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.receiverId = recipientId;
        this.receiverName = receiverName;
        this.messageContent = messageContent;
        this.timestamp = timestamp;
    }

    public MessageMemento save() {
        return new MessageMemento(messageId, senderId, senderName, receiverId, receiverName, messageContent, timestamp);
    }

    // Restore message state from a memento
    public void restore(MessageMemento memento) {
        this.messageId = memento.getMessageId();
        this.senderId = memento.getSenderId();
        this.senderName = memento.getSenderName();
        this.receiverId = memento.getReceiverId();
        this.receiverName = memento.getReceiverName();
        this.messageContent = memento.getMessageContent();
        this.timestamp = memento.getTimestamp();
    }

    public Integer getSenderId() {
        return senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getReceiverName() {
        return receiverName;
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
                ", senderId=" + senderId +
                ", senderName=" + senderName +
                ", recipientId=" + receiverId +
                ", receiverName=" + receiverName +
                ", messageContent=" + messageContent +
                ", date=" + timestamp + '}';
    }
}
