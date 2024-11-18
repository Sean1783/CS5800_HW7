package org.chatapp.chathistory;

import org.chatapp.message.Message;
import org.chatapp.messagememento.MessageMemento;
import org.chatapp.user.User;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class ChatHistory implements IterableByUser {

    private List<Message> messageHistory;
    private List<MessageMemento> undoMessageList;
    private Message poppedMessage;

    public ChatHistory() {
        messageHistory = new ArrayList<>();
        undoMessageList = new ArrayList<>();
        poppedMessage = null;
    }

    public List<Message> getMessageHistory() {
        return new ArrayList<>(messageHistory);
    }

    public void addMessageToHistory (Message message) {
        messageHistory.add(message);
        undoMessageList.add(message.save());
        if (poppedMessage != null) {
            poppedMessage = null;
        }
    }

    public void retainLastSent(Message message) {
        MessageMemento messageMemento = message.save();
        poppedMessage.restore(messageMemento);
    }

    public Message getLastSent(User user) {
        if (!messageHistory.isEmpty() && !undoMessageList.isEmpty()) {
            for (int i = messageHistory.size() - 1; i >= 0; i--) {
                Message message = messageHistory.get(i);
                if (message.getSender().equals(user)) {
                    poppedMessage = message;
                    return message;
                }
            }
        }
        return null;
    }

    public boolean removeMessageFromHistory(Message message) {
        return messageHistory.remove(message);
    }

    public Message getPoppedMessage() {
        if (poppedMessage != null) {
            return poppedMessage;
        }
        return null;
    }

    @Override
    public Iterator<Message> iterator(User userToSearchWith) {
        return new SearchMessagesByUser(messageHistory, userToSearchWith);
    }

}
