package org.chatapp.messagememento;

import org.chatapp.message.Message;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MessageMemento {

//    A class that represents a snapshot of a message sent by a user. It should have
//    properties for the message content and timestamp.

    private final int messageId;

    private Message.Endpoint sender;
//    private final int senderId;
//    private final String senderName;


//    private final int receiverId;
//    private final String receiverName;
//    Message.Endpoint receiver = null;
    Set<Message.Endpoint> recipients;
    private final String messageContent;
    private final LocalDateTime timestamp;
//    private static int counter = 0;


    public MessageMemento(Message message) {
        this.messageId = message.getMessageId();

        this.sender = message.getSender();
//        this.senderId = message.getSenderId();
//        this.senderName = message.getSenderName();
//

        this.recipients = message.getRecipients();
//        this.receiver = message.getReceiver();
//        this.receiverId = recipientId;
//        this.receiverName = receiverName;
        this.messageContent = message.getMessageContent();
        this.timestamp = message.getTimestamp();
    }


//    public MessageMemento(int messageId, int senderId, String senderName, int recipientId, String receiverName, String messageContent, LocalDateTime timestamp) {
//        this.messageId = messageId;
//        this.senderId = senderId;
//        this.senderName = senderName;
////        this.receiverId = recipientId;
////        this.receiverName = receiverName;
//        this.messageContent = messageContent;
//        this.timestamp = timestamp;
//    }

    public int getMessageId() {
        return messageId;
    }

    public Message.Endpoint getSender() {
        return sender;
    }

//    public int getSenderId() {
//        return senderId;
//    }
//
//    public String getSenderName() {
//        return senderName;
//    }



//    public Message.Endpoint getReceiver() {
//        return receiver;
//    }

    public Set<Message.Endpoint> getRecipients() {
        return recipients;
    }

//    public int getReceiverId() {
//        return receiver.getId();
//    }


    public String getMessageContent() {
        return messageContent;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
