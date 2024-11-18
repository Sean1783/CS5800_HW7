import org.chatapp.chathistory.ChatHistory;
import org.chatapp.chatserver.ChatServer;
import org.chatapp.message.Message;
import org.chatapp.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserTest {

    private ChatServer chatServer;
    private Message message;
    private User userOne;
    private User userTwo;

    @BeforeEach
    void setup() {
        chatServer = new ChatServer();
        userOne = new User("User One");
        userTwo = new User("User Two");
        Set<User> recipients = new HashSet<>();
        recipients.add(userTwo);
        String testContent = "Test content.";
        LocalDateTime timestamp = LocalDateTime.now();
        message = new Message(0, userOne, recipients, testContent, timestamp);
    }

    @Test
    public void testRegister() {
        int userId = userOne.getId();
        userOne.register(chatServer);
        Map<Integer, User> users = chatServer.getRegisteredUsers();
        assertTrue(users.containsKey(userId));
    }

    @Test
    public void testUnregister() {
        userOne.register(chatServer);
        userTwo.register(chatServer);
        userOne.unregister(chatServer);
        assertFalse(chatServer.getRegisteredUsers().containsKey(userOne.getId()));
        assertTrue(chatServer.getRegisteredUsers().containsKey(userTwo.getId()));
    }

    @Test
    public void testSendMessage() {
        userOne.register(chatServer);
        userTwo.register(chatServer);
        Set<User> recipients = new HashSet<>();
        recipients.add(userTwo);
        String messageContent = "Test Message";
        userOne.sendMessage(messageContent, recipients, chatServer);
        List<Message> userOneChatHistory = userOne.getChatHistory();
        List<Message> userTwoChatHistory = userTwo.getChatHistory();
        Message userOneMessage = userOneChatHistory.get(0);
        Message userTwoMessage = userTwoChatHistory.get(0);
        assertEquals(userOneMessage.toString(), userTwoMessage.toString());
    }

    @Test
    public void addMessageToHistoryTest() {
        userOne.addMessageToHistory(message);
        List<Message> userOneChatHistory = userOne.getChatHistory();
        assertEquals(userOneChatHistory.get(0), message);
    }

    @Test
    public void deleteMessageFromHistoryTest() {
        userOne.addMessageToHistory(message);
        userOne.deleteMessageFromHistory(message);
        List<Message> userOneChatHistory = userOne.getChatHistory();
        assertTrue(userOneChatHistory.isEmpty());
    }

    @Test
    public void undoLastMessage() {

        userOne.register(chatServer);
        userTwo.register(chatServer);
        userOne.addMessageToHistory(message);
        userTwo.addMessageToHistory(message);
        userOne.undoLastMessage(chatServer);
        List<Message> userOneChatHistory = userOne.getChatHistory();
        List<Message> userTwoChatHistory = userTwo.getChatHistory();
        assertTrue(userOneChatHistory.isEmpty());
        assertTrue(userTwoChatHistory.isEmpty());
    }


}