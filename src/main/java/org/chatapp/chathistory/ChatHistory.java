package org.chatapp.chathistory;

import org.chatapp.message.Message;
import org.chatapp.messagememento.MessageMemento;
import org.chatapp.user.User;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class ChatHistory implements IterableByUser {

    private List<Message> sentMessages;
    private List<MessageMemento> undoMessageList;
    private List<Message> receivedMessages;
    private Message poppedMessage;

    public ChatHistory() {
        sentMessages = new ArrayList<>();
        undoMessageList = new ArrayList<>();
        receivedMessages = new ArrayList<>();
        poppedMessage = null;
    }

    public void addSentMessage(Message message) {
        sentMessages.add(message);
        undoMessageList.add(message.save());
        if (poppedMessage != null) {
            poppedMessage = null;
        }
    }

    public void addReceivedMessage(Message message) {
        receivedMessages.add(message);
    }

    public boolean revokeMessage(Message message) {
        return receivedMessages.remove(message);
    }

    public Message undoLastMessage() {
        if (!sentMessages.isEmpty() && !undoMessageList.isEmpty()) {
            poppedMessage = sentMessages.remove(sentMessages.size() - 1);
            MessageMemento lastMemento = undoMessageList.remove(undoMessageList.size() - 1);
            poppedMessage.restore(lastMemento);
            return poppedMessage;
        }
        return null;
    }

    public Message getPoppedMessage() {
        if (poppedMessage != null) {
            return poppedMessage;
        }
        return null;
    }

    @Override
    public Iterator<Message> iterator(User userToSearchWith) {
        List<Message> sent = new ArrayList<>(sentMessages);
        List<Message> received = new ArrayList<>(receivedMessages);
        return new SearchMessagesByUser(sent, received, userToSearchWith);
    }

}
