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
    private Message lastRecalledMessage;

    public ChatHistory() {
        messageHistory = new ArrayList<>();
        undoMessageList = new ArrayList<>();
        lastRecalledMessage = null;
    }

    public List<Message> getMessageHistory() {
        return new ArrayList<>(messageHistory);
    }

    public List<MessageMemento> getUndoMessageList() {
        return new ArrayList<>(undoMessageList);
    }

    public Message getLastRecalledMessage() {
        if (lastRecalledMessage != null) {
            return lastRecalledMessage;
        }
        System.out.println("Cannot get last recalled Message");
        throw new IllegalStateException();
    }

    public void addMessageToHistory (Message message) {
        messageHistory.add(message);
        undoMessageList.add(message.save());
        if (lastRecalledMessage != null) {
            lastRecalledMessage = null;
        }
    }

    public void retainLastSent(Message message) {
        MessageMemento messageMemento = message.save();
        lastRecalledMessage = new Message();
        lastRecalledMessage.restore(messageMemento);
    }

    public Message getLastSent(User user) {
        if (!messageHistory.isEmpty() && !undoMessageList.isEmpty()) {
            for (int i = messageHistory.size() - 1; i >= 0; i--) {
                Message message = messageHistory.get(i);
                if (message.getSender().equals(user)) {
                    lastRecalledMessage = message;
                    return message;
                }
            }
        }
        System.out.println("Cannot get last recalled Message");
        throw new IllegalStateException();
    }

    public boolean removeMessageFromHistory(Message message) {
        return messageHistory.remove(message);
    }

    @Override
    public Iterator<Message> iterator(User userToSearchWith) {
        return new SearchMessagesByUser(messageHistory, userToSearchWith);
    }

}
