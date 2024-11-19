package org.chatapp.chathistory;
import org.chatapp.message.Message;
import org.chatapp.user.User;

import java.util.Iterator;

public interface IterableByUser {
    Iterator<Message> iterator(User userToSearchWith);
}
