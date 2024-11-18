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

    public void addMessageToHistory (Message message) {
        messageHistory.add(message);
        undoMessageList.add(message.save());
    }

    public Message removeLastSent(User user) {

        Message message;
        MessageMemento messageMemento;
        if (!messageHistory.isEmpty()) {
            for (int i = messageHistory.size() - 1; i >= 0; i--) {
                message = messageHistory.get(i);
                messageMemento = undoMessageList.get(i);
                if (message.getSender().equals(user)) {
                    messageHistory.remove(message);
                    undoMessageList.remove(messageMemento);
                    poppedMessage = message;
                    return message;
                }
            }
        }
        return null;
    }

    public boolean revokeMessage (Message message) {
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
