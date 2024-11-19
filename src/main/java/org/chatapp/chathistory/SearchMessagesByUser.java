package org.chatapp.chathistory;

import org.chatapp.message.Message;
import org.chatapp.user.User;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SearchMessagesByUser implements Iterator<Message> {

    private final Iterator<Message> messageIterator;
    private final User userToSearchWith;
    private Message nextMessage;

    public SearchMessagesByUser(List<Message> messageHistory, User user) {
        this.messageIterator = messageHistory.iterator();
        this.userToSearchWith = user;
        nextMessage = null;
        advance();
    }

    public void advance() {
        nextMessage = null;
        while (messageIterator.hasNext()) {
            Message currentMessage = messageIterator.next();
            Set<User> recipients = currentMessage.getRecipients();
            boolean containsTargetId = false;
            for (User recipient : recipients) {

                if (recipient == userToSearchWith) {
                    containsTargetId = true;
                    break;
                }
            }
            if (currentMessage.getSender()== userToSearchWith || containsTargetId) {
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
