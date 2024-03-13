package edu.arep;
import edu.arep.logservice.LogService;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

public class LogServiceTest {

    @Test
    public void testSendMessageWithoutMessage() {
        List<String> messages = LogService.sendMessage(null);
        assertNotNull(messages);
        assertFalse(messages.isEmpty());
    }

    @Test
    public void testSendMessageWithEmptyMessage() {
        List<String> messages = LogService.sendMessage("");
        assertNotNull(messages);
        assertFalse(messages.isEmpty());
    }

    @Test
    public void testSendMessageWithNullStringMessage() {
        List<String> messages = LogService.sendMessage("null");
        assertNotNull(messages);
        assertFalse(messages.isEmpty());
    }


}