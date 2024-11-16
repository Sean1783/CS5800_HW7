package org.chatapp.message;

import org.chatapp.messagememento.MessageMemento;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Message {
    private int messageId;

    private Endpoint sender;
//    private int senderId;
//    private String senderName;
//

//    private int receiverId;
//    private String receiverName;
//    Endpoint receiver;
    Set<Endpoint> recipients;
    private String messageContent;
    private LocalDateTime timestamp;
//    private static int counter = 0;

//    public Message(int messageId, int senderId, String senderName, int recipientId, String receiverName, String messageContent, LocalDateTime timestamp) {
//        this.messageId = messageId;
//        this.senderId = senderId;
//        this.senderName = senderName;
//        this.receiverId = recipientId;
//        this.receiverName = receiverName;
//        this.messageContent = messageContent;
//        this.timestamp = timestamp;
//    }

    public Message(int messageId, Endpoint sender, Set<Endpoint> receivers, String messageContent, LocalDateTime timestamp) {
        this.messageId = messageId;

        this.sender = sender;

//        this.senderId = sender.id;
//        this.senderName = sender.name;

//        this.receiver = null;
        this.recipients = receivers;
//        this.receiverId = recipientId;
//        this.receiverName = receiverName;
        this.messageContent = messageContent;
        this.timestamp = timestamp;
    }

//    public Message(int messageId, Endpoint sender, Endpoint receiver, String messageContent, LocalDateTime timestamp) {
//        this.messageId = messageId;
//
//        this.senderId = sender.id;
//        this.senderName = sender.name;
//        this.receiver = receiver;
//        this.recipients = new HashSet<>();
//        this.messageContent = messageContent;
//        this.timestamp = timestamp;
//    }

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

//    public Endpoint getReceiver() {
//        return receiver;
//    }

    public MessageMemento save() {
        return new MessageMemento(this);

    }

    // Restore message state from a memento
    public void restore(MessageMemento memento) {
        this.messageId = memento.getMessageId();

        this.sender = memento.getSender();

//        this.senderId = memento.getSenderId();
//        this.senderName = memento.getSenderName();


//        this.receiver = memento.getReceiver();
        this.recipients = memento.getRecipients();
//        this.receiverId = memento.getReceiverId();
//        this.receiverName = memento.getReceiverName();
        this.messageContent = memento.getMessageContent();
        this.timestamp = memento.getTimestamp();
    }

//    public Integer getSenderId() {
//        return senderId;
//    }


//    public Integer getReceiverId() {
//        return receiverId;
//    }

    public Integer getMessageId() {
        return messageId;
    }


//    public String getSenderName() {
//        return senderName;
//    }



//    public String getReceiverName() {
//        return receiverName;
//    }

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
                ", senderId=" + sender.getId() +
                ", senderName=" + sender.getName() +
                ", recipients=" + recipients +
//                ", receiverName=" + receiverName +
                ", messageContent=" + messageContent +
                ", date=" + timestamp + '}';
    }
}
