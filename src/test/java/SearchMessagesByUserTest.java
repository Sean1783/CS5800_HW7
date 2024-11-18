import org.chatapp.chathistory.SearchMessagesByUser;
import org.chatapp.message.Message;
import org.chatapp.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SearchMessagesByUserTest {

    private Message message;
    private User userOne;
    private User userTwo;
    private User userThree;
    private LocalDateTime timestamp;
    private List<Message> messageHistory;

    @BeforeEach
    public void setUp() throws Exception {

        userOne = new User("userOne test obj");
        userTwo = new User("userTwo test obj");
        userThree = new User("userThree test obj");
        timestamp = LocalDateTime.now();
        message = new Message(userOne,
                Collections.singleton(userTwo),
                "Test Message",
                timestamp);
        messageHistory = new ArrayList<>();
        messageHistory.add(message);
    }

    @Test
    public void testIteratorWithMatchingUserAsSenderOrRecipient() {
        Iterator<Message> iteratorOne = new SearchMessagesByUser(messageHistory, userOne);
        List<Message> results = new ArrayList<>();
        while (iteratorOne.hasNext()) {
            results.add(iteratorOne.next());
        }
        Iterator<Message> iteratorTwo = new SearchMessagesByUser(messageHistory, userTwo);
        while (iteratorTwo.hasNext()) {
            results.add(iteratorTwo.next());
        }
        assertEquals(2, results.size());
    }

    @Test
    public void testIteratorWithNoMatchingUser() {
        Iterator<Message> iteratorOne = new SearchMessagesByUser(messageHistory, userThree);
        List<Message> results = new ArrayList<>();
        while (iteratorOne.hasNext()) {
            results.add(iteratorOne.next());
        }
        assertTrue(results.isEmpty());
    }

}
