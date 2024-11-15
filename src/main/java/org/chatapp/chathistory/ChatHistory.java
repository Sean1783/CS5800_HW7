package org.chatapp.chathistory;

import org.chatapp.message.Message;
import org.chatapp.messagememento.MessageMemento;
import org.chatapp.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChatHistory {

//    User chatHistoryOwner;

    List<Message> sentMessages = new ArrayList<>();
    List<Message> receivedMessages = new ArrayList<>();
    List<MessageMemento> undoMessageList = new ArrayList<>();
    Message poppedMessage;

//    List<Message> messageHistory = new ArrayList<>();

    public ChatHistory(User chatHistoryOwner) {
//        this.chatHistoryOwner = chatHistoryOwner;
    }

//    public ChatHistory(int ownerId) {
//        this.ownerId = ownerId;
//    }


    public void addSentMessage(int messageId, int senderId, String senderName, int receiverId, String receiverName, String messageContent, LocalDateTime timestamp) {
        Message message = new Message(messageId, senderId, senderName, receiverId, receiverName, messageContent, timestamp);
        sentMessages.add(message);
        undoMessageList.add(message.save());
    }

    public void addReceivedMessage(int messageId, int senderId, String senderName, int receiverId, String receiverName, String messageContent, LocalDateTime timestamp) {
        Message message = new Message(messageId, senderId, senderName, receiverId, receiverName, messageContent, timestamp);
        receivedMessages.add(message);
    }

    public void revokeMessage(int messageId) {
        receivedMessages.removeIf(message -> message.getMessageId() == messageId);
    }

//    public void addMessage(int messageId, int senderId, String senderName, int receiverId, String receiverName, String messageContent, LocalDateTime timestamp) {
//        Message message = new Message(messageId, senderId, senderName, receiverId, receiverName, messageContent, timestamp);
//        messageHistory.add(message);
//        undoMessageList.add(message.save());
//    }

    // Undo the last message sent
    public int undoLastMessage() {
        if (!sentMessages.isEmpty() && !undoMessageList.isEmpty()) {
            poppedMessage = sentMessages.remove(sentMessages.size() - 1); // Remove the last message
            MessageMemento lastMemento = undoMessageList.remove(undoMessageList.size() - 1); // Remove its memento
            poppedMessage.restore(lastMemento); // Optionally restore the original state
            // Notify chatServer
            return poppedMessage.getMessageId();
        }
        return 0;
    }

    public void redoLastMessage() {
        if (poppedMessage != null) {
            sentMessages.add(poppedMessage);
            undoMessageList.add(poppedMessage.save());
            // Notify chatServer

            int senderId = poppedMessage.getSenderId();
            String senderName = poppedMessage.getSenderName();
            int receiverId = poppedMessage.getReceiverId();
            String receiverName = poppedMessage.getReceiverName();
            String messageContent = poppedMessage.getMessageContent();
            LocalDateTime timestamp = poppedMessage.getTimestamp();
        }
    }

//    public void addMessage(Message message) {
//        messageHistory.add(message);
//    }

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

//    public void removeMessage(Message message) {
//        messageHistory.remove(message);
//    }

//    public List<String> receiveMessagesFromUser(User user) {
//        List<Message> messagesFromUser = getMessagesFromUser(user);
//        List<String> stringOfMessages = new ArrayList<>();
//        for (Message message : messagesFromUser) {
//            stringOfMessages.add(message.toString());
//        }
//        return stringOfMessages;
//    }

    private List<Message> getMessagesFromUser(User user) {
        List<Message> allMessages = new ArrayList<>();
        for (Message message : receivedMessages) {
            if (message.getSenderId().equals(user.getId())) {
                allMessages.add(message);
            }
        }
        return allMessages;
    }

    private List<Message> getMessagesToUser(User user) {
        List<Message> allMessages = new ArrayList<>();
        for (Message message : sentMessages) {
            if (message.getReceiverId().equals(user.getId())) {
                allMessages.add(message);
            }
        }
        return allMessages;
    }

}
