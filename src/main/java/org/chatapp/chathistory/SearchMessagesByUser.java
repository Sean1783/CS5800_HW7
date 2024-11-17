package org.chatapp.chathistory;

import org.chatapp.message.Message;
import org.chatapp.user.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SearchMessagesByUser implements Iterator<Message> {

    private final Iterator<Message> messageIterator;
    private final User userToSearchWith;
    private Message nextMessage;
    private List<Message> combinedMessageList;

    public SearchMessagesByUser(List<Message> sentMessages, List<Message> receivedMessages, User user) {
        combinedMessageList = consolidateMessageLists(sentMessages, receivedMessages);
        this.messageIterator = combinedMessageList.iterator();
        this.userToSearchWith = user;
        nextMessage = null;
        advance();
    }

    private List<Message> consolidateMessageLists(List<Message> sentMessages, List<Message> receivedMessages) {

        List<Message> chatHistory = new ArrayList<>();
        int sentIndex = 0;
        int receivedIndex = 0;
        while (sentIndex < sentMessages.size() && receivedIndex < receivedMessages.size()) {
            Message sentMessage = sentMessages.get(sentIndex);
            Message receivedMessage = receivedMessages.get(receivedIndex);
            if (sentMessage.getTimestamp().isBefore(receivedMessage.getTimestamp())) {
                chatHistory.add(sentMessage);
                sentIndex++;
            } else {
                chatHistory.add(receivedMessage);
                receivedIndex++;
            }
        }
        while (sentIndex < sentMessages.size()) {
            chatHistory.add(sentMessages.get(sentIndex));
            sentIndex++;
        }
        while (receivedIndex < receivedMessages.size()) {
            chatHistory.add(receivedMessages.get(receivedIndex));
            receivedIndex++;
        }
        return chatHistory;
    }

    public void advance() {
        nextMessage = null;
        while (messageIterator.hasNext()) {
            Message currentMessage = messageIterator.next();
            Set<Message.Endpoint> recipients = currentMessage.getRecipients();
            boolean containsTargetId = false;
            for (Message.Endpoint recipient : recipients) {
                if (recipient.getId() == userToSearchWith.getId()) {
                    containsTargetId = true;
                    break;
                }
            }
            if (currentMessage.getSender().getId() == userToSearchWith.getId() || containsTargetId) {
                nextMessage = currentMessage;
                break;
            }
        }
    }

    @Override
    public boolean hasNext() {
        return nextMessage != null;
    }

    @Override
    public Message next() {
        if (nextMessage == null) {
            throw new IllegalStateException("No more messages.");
        }
        Message current = nextMessage;
        advance();
        return current;
    }

}
