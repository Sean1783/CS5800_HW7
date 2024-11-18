import org.chatapp.message.Message;
import org.chatapp.messagememento.MessageMemento;
import org.chatapp.user.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MessageTest {
    private Message message;
    private User senderMock;
    private User recipientMock;
    private LocalDateTime timestamp;

    @BeforeEach
    public void setUp() {
        senderMock = mock(User.class);
        recipientMock = mock(User.class);
        timestamp = LocalDateTime.now();
        message = new Message(senderMock,
                Collections.singleton(recipientMock),
                "Test Message",
                timestamp);
    }

    @Test
    public void testInit() {
        assertEquals(senderMock, message.getSender());
        assertEquals(Collections.singleton(recipientMock), message.getRecipients());
        assertEquals("Test Message", message.getMessageContent());
        assertEquals(timestamp, message.getTimestamp());
    }

    @Test
    public void testSave() {
        MessageMemento memento = message.save();
        assertEquals(senderMock, memento.getSender());
        assertEquals(Collections.singleton(recipientMock), memento.getRecipients());
        assertEquals("Test Message", memento.getMessageContent());
        assertEquals(timestamp, memento.getTimestamp());
    }

    @Test
    public void testRestore() {
        MessageMemento memento = new MessageMemento(
                new Message(senderMock,
                        Collections.singleton(recipientMock),
                        "Restored Message",
                        timestamp.minusDays(1)));
        message.restore(memento);
        assertEquals("Restored Message", message.getMessageContent());
        assertEquals(timestamp.minusDays(1), message.getTimestamp());
    }
}
