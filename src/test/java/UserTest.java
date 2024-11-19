import org.chatapp.chatserver.ChatServer;
import org.chatapp.message.Message;
import org.chatapp.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

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
        message = new Message(userOne, recipients, testContent, timestamp);
    }

    @Test
    public void testRegister() {
        userOne.register(chatServer);
        List<User> users = chatServer.getRegisteredUsers();
        assertTrue(users.contains(userOne));
    }

    @Test
    public void testUnregister() {
        userOne.register(chatServer);
        userTwo.register(chatServer);
        userOne.unregister(chatServer);
        assertFalse(chatServer.getRegisteredUsers().contains(userOne));
        assertTrue(chatServer.getRegisteredUsers().contains(userTwo));
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
    public void testAddMessageToHistory() {
        userOne.addMessageToHistory(message);
        List<Message> userOneChatHistory = userOne.getChatHistory();
        assertEquals(userOneChatHistory.get(0), message);
    }

    @Test
    public void testDeleteMessageFromHistoryTest() {
        userOne.addMessageToHistory(message);
        userOne.deleteMessageFromHistory(message);
        List<Message> userOneChatHistory = userOne.getChatHistory();
        assertTrue(userOneChatHistory.isEmpty());
    }

    @Test
    public void testUndoLastMessage() {
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

    @Test
    public void testRetainLastSent() {
        userOne.addMessageToHistory(message);
        userOne.retainLastSent(message);
        Message lastSentMessage = userOne.getLastRecalledMessage();
        assertEquals(lastSentMessage.getMessageContent(), message.getMessageContent());
    }

    @Test
    public void testRedoLastMessage() {
        userOne.register(chatServer);
        userTwo.register(chatServer);
        userOne.addMessageToHistory(message);
        userTwo.addMessageToHistory(message);
        userOne.undoLastMessage(chatServer);
        userOne.redoLastMessage(chatServer);
        List<Message> userTwoChatHistory = userTwo.getChatHistory();
        assertFalse(userTwoChatHistory.isEmpty());
    }

    @Test
    public void testBlockUser() {
        userOne.register(chatServer);
        userTwo.register(chatServer);
        userTwo.blockUser(userOne, chatServer);
        Set<User> recipients = new HashSet<>();
        recipients.add(userTwo);
        String messageContent = "Blocked message content";
        userOne.sendMessage(messageContent, recipients, chatServer);
        List<Message> userTwoChatHistory = userTwo.getChatHistory();
        assertTrue(userTwoChatHistory.isEmpty());
        Map<User, Set<User>> blockedUsers = chatServer.getBlockedUsers();
        Set<User> blockedUser = blockedUsers.get(userTwo);
        assertTrue(blockedUser.contains(userOne));
    }

    @Test
    public void testUnblockUser() {
        userOne.register(chatServer);
        userTwo.register(chatServer);
        userTwo.blockUser(userOne, chatServer);
        Set<User> recipients = new HashSet<>();
        recipients.add(userTwo);
        String messageContent = "Blocked message content";
        userOne.sendMessage(messageContent, recipients, chatServer);
        userTwo.unblockUser(userOne, chatServer);
        userOne.sendMessage(messageContent, recipients, chatServer);
        List<Message> userTwoChatHistory = userTwo.getChatHistory();
        assertEquals(1, userTwoChatHistory.size());
        Map<User, Set<User>> blockedUsers = chatServer.getBlockedUsers();
        Set<User> blockedUser = blockedUsers.get(userTwo);
        assertFalse(blockedUser.contains(userOne));
    }

    @Test
    public void testIterator() {
        userOne.register(chatServer);
        userTwo.register(chatServer);
        userOne.addMessageToHistory(message);
        userTwo.addMessageToHistory(message);
        Iterator<Message> messageIterator = userOne.iterator(userTwo);
        assertTrue(messageIterator.hasNext());
        Message iteratorMessage = messageIterator.next();
        assertEquals(iteratorMessage, message);
    }


}