package org.chatapp.chathistory;

import org.chatapp.message.Message;
import org.chatapp.messagememento.MessageMemento;
import org.chatapp.user.User;

import java.util.ArrayList;
import java.util.List;

public class ChatHistory {

    int ownerId;
    MessageMemento messageMemento;
    List<Message> messageHistory = new ArrayList<>();

    public ChatHistory(int ownerId) {
        this.ownerId = ownerId;
    }

    public void addMessage(Message message) {
        messageHistory.add(message);
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
            if (sentMessage.getDate().isBefore(receivedMessage.getDate())) {
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

    public List<String> receiveMessagesFromUser(User user) {
        List<Message> messagesFromUser = getMessagesFromUser(user);
        List<String> stringOfMessages = new ArrayList<>();
        for (Message message : messagesFromUser) {
            stringOfMessages.add(message.toString());
        }
        return stringOfMessages;
    }

    private List<Message> getMessagesFromUser(User user) {
        List<Message> allMessages = new ArrayList<>();
        for (Message message : messageHistory) {
            if (message.getSenderId().equals(user.getId())) {
                allMessages.add(message);
            }
        }
        return allMessages;
    }

    private List<Message> getMessagesToUser(User user) {
        List<Message> allMessages = new ArrayList<>();
        for (Message message : messageHistory) {
            if (message.getReceiverId().equals(user.getId())) {
                allMessages.add(message);
            }
        }
        return allMessages;
    }

}
