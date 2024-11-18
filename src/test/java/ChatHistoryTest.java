import org.chatapp.chathistory.ChatHistory;
import org.chatapp.message.Message;
import org.chatapp.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class ChatHistoryTest {

    ChatHistory chatHistory;
    private Message message;
    private User userOne;
    private User userTwo;
    private LocalDateTime timestamp;

    @BeforeEach
    public void setUp() throws Exception {
        chatHistory = new ChatHistory();
        userOne = new User("userOne test obj");
        userTwo = new User("userTwo test obj");
        timestamp = LocalDateTime.now();
        message = new Message(userOne,
                Collections.singleton(userTwo),
                "Test Message",
                timestamp);
    }

    @Test
    public void testAddMessageToHistory() {
        chatHistory.addMessageToHistory(message);
        assertTrue(chatHistory.getMessageHistory().contains(message));
        assertFalse(chatHistory.getUndoMessageList().isEmpty());
    }

    @Test
    public void testRetainLastSent() {
        chatHistory.retainLastSent(message);
        assertEquals(chatHistory.getLastRecalledMessage().toString(), message.toString());
    }

    @Test
    public void testGetLastSent() {
        chatHistory.addMessageToHistory(message);
        chatHistory.getLastSent(userOne);
        assertEquals(chatHistory.getLastSent(userOne), message);
    }

    @Test
    public void testRemoveMessageFromHistory() {
        chatHistory.addMessageToHistory(message);
        assertTrue(chatHistory.removeMessageFromHistory(message));
        assertFalse(chatHistory.removeMessageFromHistory(message));
    }

}
