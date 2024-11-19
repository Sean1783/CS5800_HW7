import org.chatapp.chatserver.ChatServer;
import org.chatapp.message.Message;
import org.chatapp.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class ChatServerTest {

    private ChatServer server;
    private Message message;
    private User userOne;
    private User userTwo;
    private LocalDateTime timestamp;

    @BeforeEach
    public void setUp() throws Exception {
        server = new ChatServer();
        userOne = new User("userOne test obj");
        userTwo = new User("userTwo test obj");
        timestamp = LocalDateTime.now();
        message = new Message(userOne,
                Collections.singleton(userTwo),
                "Test Message",
                timestamp);
    }

    @Test
    public void testRegisterUser() {
        server.registerUser(userOne);
        assertEquals(server.getRegisteredUsers().get(0), userOne);
    }

    @Test
    public void testUnregisterUser() {
        server.registerUser(userOne);
        server.unregisterUser(userOne);
        assertTrue(server.getRegisteredUsers().isEmpty());
    }

    @Test
    public void testSendMessage() {
        server.registerUser(userOne);
        server.registerUser(userTwo);
        String messageContent = "Test Message";
        server.sendMessage(userOne,Collections.singleton(userTwo),messageContent);
        assertFalse(server.getGlobalMessageRecord().isEmpty());
    }

    @Test
    public void testNullMessage() {
        assertThrows(IllegalArgumentException.class, () -> server.redo(userOne, null));
        assertThrows(IllegalArgumentException.class, () -> server.revokeMessage(null));
    }

    @Test
    public void testRedo() {
        server.registerUser(userOne);
        server.registerUser(userTwo);
        server.redo(userOne, message);
        assertFalse(server.getGlobalMessageRecord().isEmpty());
    }

    @Test
    public void testRevokeMessage() {
        server.registerUser(userOne);
        server.registerUser(userTwo);
        String messageContent = "Test Message";
        server.sendMessage(userOne,Collections.singleton(userTwo),messageContent);
        Message sentMessage = server.getGlobalMessageRecord().get(0);
        server.revokeMessage(sentMessage);
        assertTrue(userOne.getChatHistory().isEmpty());
        assertTrue(userTwo.getChatHistory().isEmpty());
    }

    @Test
    public void testBlockUsers() {
        server.registerUser(userOne);
        server.registerUser(userTwo);
        server.blockUsers(userTwo, userOne);
        assertEquals(server.getBlockedUsers().get(userTwo), Collections.singleton(userOne));
    }

    @Test
    public void testUnblockUsers() {
        server.registerUser(userOne);
        server.registerUser(userTwo);
        server.blockUsers(userTwo, userOne);
        server.unblockUsers(userTwo, userOne);
        assertTrue(server.getBlockedUsers().get(userTwo).isEmpty());
    }

    @Test
    public void testIsUserRegistered() {
        server.registerUser(userOne);
        assertTrue(server.isUserRegistered(userOne));
        assertFalse(server.isUserRegistered(userTwo));
    }

    @Test
    public void testSenderIsBlocked() {
        server.registerUser(userOne);
        server.registerUser(userTwo);
        server.blockUsers(userTwo, userOne);
        assertTrue(server.senderIsBlocked(userOne, userTwo));
        assertFalse(server.senderIsBlocked(userTwo, userOne));
    }

    @Test
    public void testValidateRecipient() {
        server.registerUser(userOne);
        assertFalse(server.validateRecipient(userOne, userTwo));
    }

}
