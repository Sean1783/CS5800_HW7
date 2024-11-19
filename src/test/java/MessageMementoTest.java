import org.chatapp.message.Message;
import org.chatapp.messagememento.MessageMemento;
import org.chatapp.user.User;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MessageMementoTest {
    private Message messageMock;
    private User senderMock;
    private User recipientMock;
    private LocalDateTime timestamp;

    @BeforeEach
    void setUp() {
        senderMock = mock(User.class);
        recipientMock = mock(User.class);
        timestamp = LocalDateTime.now();
        messageMock = mock(Message.class);
        when(messageMock.getSender()).thenReturn(senderMock);
        when(messageMock.getRecipients()).thenReturn(Collections.singleton(recipientMock));
        when(messageMock.getMessageContent()).thenReturn("Mocked Message");
        when(messageMock.getTimestamp()).thenReturn(timestamp);
    }

    @Test
    void testInit() {
        MessageMemento memento = new MessageMemento(messageMock);
        assertEquals(senderMock, memento.getSender());
        assertEquals(Collections.singleton(recipientMock), memento.getRecipients());
        assertEquals("Mocked Message", memento.getMessageContent());
        assertEquals(timestamp, memento.getTimestamp());
    }

}