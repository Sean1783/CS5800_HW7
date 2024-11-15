package org.chatapp.message;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Message {
    private final int messageId;
    private final int senderId;
    private final String senderName;
    private final int receiverId;
    private final String receiverName;
    private final String messageContent;
    private final LocalDateTime date;
    private static int counter = 0;

    public Message(Integer senderId, String senderName, Integer recipientId, String receiverName, String messageContent) {
        this.messageId = counter++;
        this.senderId = senderId;
        this.senderName = senderName;
        this.receiverId = recipientId;
        this.receiverName = receiverName;
        this.messageContent = messageContent;
        this.date = LocalDateTime.now();
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

    public LocalDateTime getDate() {
        return date;
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
                ", date=" + date + '}';
    }
}
