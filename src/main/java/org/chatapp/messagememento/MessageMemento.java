package org.chatapp.messagememento;

import org.chatapp.message.Message;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessageMemento {

    List<Message> fullHistory = new ArrayList<>();
    List<Message> mutableHistory = new ArrayList<>();

//    A class that represents a snapshot of a message sent by a user. It should have
//    properties for the message content and timestamp.

    private final int messageId;
    private final int senderId;
    private final String senderName;
    private final int receiverId;
    private final String receiverName;
    private final String messageContent;
    private final LocalDateTime timestamp;
//    private static int counter = 0;

    public MessageMemento(int messageId, int senderId, String senderName, int recipientId, String receiverName, String messageContent, LocalDateTime timestamp) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.receiverId = recipientId;
        this.receiverName = receiverName;
        this.messageContent = messageContent;
        this.timestamp = timestamp;
    }

    public int getMessageId() {
        return messageId;
    }

    public int getSenderId() {
        return senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public int getReceiverId() {
        return receiverId;
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
}
