import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        UserTest.class,
        MessageTest.class,
        MessageMementoTest.class,
        ChatServerTest.class,
        ChatHistoryTest.class,
        SearchMessagesByUserTest.class
}) public class CompleteChatAppTest {}
