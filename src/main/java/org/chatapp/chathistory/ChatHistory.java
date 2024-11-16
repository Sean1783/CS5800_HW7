package org.chatapp.chathistory;

import org.chatapp.message.Message;
import org.chatapp.messagememento.MessageMemento;
import org.chatapp.user.User;

import java.time.LocalDateTime;
import java.util.*;

public class ChatHistory {

    List<Message> sentMessages = new ArrayList<>();
    List<MessageMemento> undoMessageList = new ArrayList<>();
    List<Message> receivedMessages = new ArrayList<>();
    Message poppedMessage;


//    public void addSentMessage(int messageId, int senderId, String senderName, int receiverId, String receiverName, String messageContent, LocalDateTime timestamp) {
//        Message message = new Message(messageId, senderId, senderName, receiverId, receiverName, messageContent, timestamp);
//        sentMessages.add(message);
//        undoMessageList.add(message.save());
//    }

    public void addSentMessage(Message message) {
        sentMessages.add(message);
        undoMessageList.add(message.save());
        if (!(poppedMessage == null)) {
            poppedMessage = null;
        }
    }



    public void addReceivedMessage(int messageId, Message.Endpoint sender, Set<Message.Endpoint> recipients, String messageContent, LocalDateTime timestamp) {
        Message message = new Message(messageId, sender, recipients, messageContent, timestamp);
        receivedMessages.add(message);
    }


//    public void addReceivedMessage(int messageId, Message.Endpoint sender, Message.Endpoint receiver, String messageContent, LocalDateTime timestamp) {
//        Message message = new Message(messageId, sender, receiver, messageContent, timestamp);
//        receivedMessages.add(message);
//    }



//    public void addReceivedMessage(int messageId, int senderId, String senderName, int receiverId, String receiverName, String messageContent, LocalDateTime timestamp) {
//        Message message = new Message(messageId, senderId, senderName, receiverId, receiverName, messageContent, timestamp);
//        receivedMessages.add(message);
//    }

    public void addReceivedMessage(Message message) {
        receivedMessages.add(message);
    }

    public void revokeMessage(int messageId) {
        receivedMessages.removeIf(message -> message.getMessageId() == messageId);
    }

    public int undoLastMessage() {
        if (!sentMessages.isEmpty() && !undoMessageList.isEmpty()) {

            poppedMessage = sentMessages.remove(sentMessages.size() - 1); // Remove the last message
            MessageMemento lastMemento = undoMessageList.remove(undoMessageList.size() - 1); // Remove its memento
            poppedMessage.restore(lastMemento); // Optionally restore the original state
            return poppedMessage.getMessageId();
        }
        return 0;
    }

    public Message redo() {
        if (poppedMessage != null) {
//            sentMessages.add(poppedMessage);
//            undoMessageList.add(poppedMessage.save());

//            int senderId = poppedMessage.getSenderId();
//            String senderName = poppedMessage.getSenderName();
//            int receiverId = poppedMessage.getReceiverId();
//            String receiverName = poppedMessage.getReceiverName();
//            String messageContent = poppedMessage.getMessageContent();
//            LocalDateTime timestamp = poppedMessage.getTimestamp();

            // Notify chatServer
            return poppedMessage;
        }
        return null;
    }

    public List<String> getChatHistoryWithUser(User user) {
        List<Message> sentMessages = getMessagesToUser(user);
        List<Message> receivedMessages = getMessagesFromUser(user);
        List<String> chatHistory = new ArrayList<>();
        int sentIndex = 0;
        int receivedIndex = 0;
        while (sentIndex < sentMessages.size() && receivedIndex < receivedMessages.size()) {
            Message sentMessage = sentMessages.get(sentIndex);
            Message receivedMessage = receivedMessages.get(receivedIndex);
            if (sentMessage.getTimestamp().isBefore(receivedMessage.getTimestamp())) {
                chatHistory.add(sentMessage.toString());
                sentIndex++;
            } else {
                chatHistory.add(receivedMessage.toString());
                receivedIndex++;
            }
        }
        while (sentIndex < sentMessages.size()) {
            chatHistory.add(sentMessages.get(sentIndex).toString());
            sentIndex++;
        }
        while (receivedIndex < receivedMessages.size()) {
            chatHistory.add(receivedMessages.get(receivedIndex).toString());
            receivedIndex++;
        }
        return chatHistory;
    }

    private List<Message> getMessagesFromUser(User user) {
        List<Message> allMessages = new ArrayList<>();
        int sendingUserId = user.getId();
        for (Message message : receivedMessages) {
            int messageSenderId = message.getSender().getId();
            if (messageSenderId == sendingUserId) {
                allMessages.add(message);
            }
        }
        return allMessages;
    }

    private List<Message> getMessagesToUser(User user) {
        List<Message> allMessages = new ArrayList<>();
        for (Message message : sentMessages) {
//            Message.Endpoint receiver = message.getReceiver();
            Set<Message.Endpoint> recipients = message.getRecipients();
            if (!recipients.isEmpty()) {
                for (Message.Endpoint recipient : recipients) {
                    if (recipient.getId() == user.getId()) {
                        allMessages.add(message);
                    }
                }
            }
        }
        return allMessages;
    }
}
