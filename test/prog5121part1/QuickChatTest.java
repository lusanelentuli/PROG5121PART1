package prog5121part1;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class QuickChatTest {

    @Test
    public void testSearchMessageByID() {

        List<Message> messages = new ArrayList<>();

        Message message = new Message(
                0, "+27834557896", "Did you get the cake?"
        );

        messages.add(message);

        String result = QuickChat.searchMessageByID(
                messages, message.getMessageID()
        );

        assertTrue(result.contains("Did you get the cake?"));
    }

    @Test
    public void testSearchMessagesByRecipient() {

        List<Message> messages = new ArrayList<>();

        messages.add(new Message(
                0, "+27838884567",
                "Where are you? You are late! I have asked you to be on time."
        ));

        messages.add(new Message(
                1, "+27838884567",
                "Ok, I am leaving without you."
        ));

        String result = QuickChat.searchMessagesByRecipient(
                messages, "+27838884567"
        );

        assertTrue(result.contains(
                "Where are you? You are late! I have asked you to be on time."
        ));

        assertTrue(result.contains("Ok, I am leaving without you."));
    }

    @Test
    public void testDeleteMessageByHash() {

        List<Message> messages = new ArrayList<>();

        Message message = new Message(
                0, "+27838884567",
                "Where are you? You are late!"
        );

        messages.add(message);

        String result = QuickChat.deleteMessageByHash(
                messages, message.getMessageHash()
        );

        assertEquals("Message successfully deleted.", result);
        assertTrue(messages.isEmpty());
    }

    @Test
    public void testDisplayLongestStoredMessage() {

        List<Message> messages = new ArrayList<>();

        messages.add(new Message(
                0, "+27834557896", "Did you get the cake?"
        ));

        messages.add(new Message(
                1, "+27838884567",
                "Where are you? You are late! I have asked you to be on time."
        ));

        messages.add(new Message(
                2, "+27834484567",
                "It is dinner time!"
        ));

        String result = QuickChat.displayLongestStoredMessage(messages);

        assertTrue(result.contains(
                "Where are you? You are late! I have asked you to be on time."
        ));
    }
    @Test
public void testReadStoredMessagesPopulatesArrays() {

    QuickChat.readStoredMessages();

    Message[] sent = QuickChat.getSentMessagesArray();
    Message[] disregarded = QuickChat.getDisregardedMessagesArray();
    Message[] stored = QuickChat.getStoredMessagesArray();

    String[] hashes = QuickChat.getMessageHashesArray();
    String[] ids = QuickChat.getMessageIDsArray();

    int totalMessages = sent.length
            + disregarded.length
            + stored.length;

    assertEquals(totalMessages, hashes.length);
    assertEquals(totalMessages, ids.length);
}
@Test
public void testMessagesArePlacedInCorrectArrays() {

    QuickChat.readStoredMessages();

    Message[] sent = QuickChat.getSentMessagesArray();
    Message[] disregarded = QuickChat.getDisregardedMessagesArray();
    Message[] stored = QuickChat.getStoredMessagesArray();

    assertEquals(2, sent.length);
    assertEquals(1, disregarded.length);
    assertEquals(2, stored.length);

   assertTrue(stored[0].getMessage().contains("Ok, I am leaving"));
   assertTrue(stored[1].getMessage().contains("Where are you?"));
}
@Test
public void testLongestStoredMessageFromArray() {

    QuickChat.readStoredMessages();

    Message[] stored = QuickChat.getStoredMessagesArray();

    String result = QuickChat.displayLongestStoredMessage(
            java.util.Arrays.asList(stored)
    );

    assertTrue(result.contains(
            "Where are you? You are late! I have asked you to be on time."
    ));
}
@Test
public void testSearchSentAndStoredMessagesByRecipient() {

    QuickChat.readStoredMessages();

    List<Message> searchableMessages = new ArrayList<>();

    searchableMessages.addAll(
            java.util.Arrays.asList(QuickChat.getSentMessagesArray())
    );

    searchableMessages.addAll(
            java.util.Arrays.asList(QuickChat.getStoredMessagesArray())
    );

    String result = QuickChat.searchMessagesByRecipient(
            searchableMessages, "+27838884567"
    );

    assertTrue(result.contains(
            "Where are you? You are late! I have asked you to be on time."
    ));
}
@Test
public void testMessageReportDisplaysRequiredDetails() {

    Message message = new Message(
            0,
            "+27834557896",
            "Did you get the cake?"
    );

    java.io.ByteArrayOutputStream output =
            new java.io.ByteArrayOutputStream();

    java.io.PrintStream originalOutput = System.out;

    try {
        System.setOut(new java.io.PrintStream(output));

        QuickChat.printReportDetails(message);

    } finally {
        System.setOut(originalOutput);
    }

    String report = output.toString();

    assertTrue(report.contains("Message Hash:"));
    assertTrue(report.contains("Recipient: +27834557896"));
    assertTrue(report.contains("Message: Did you get the cake?"));
}
@Test
public void testStoredMessageSenderAndRecipient() {

    QuickChat.readStoredMessages();

    Message[] stored = QuickChat.getStoredMessagesArray();

    assertTrue(stored.length > 0);

    for (Message message : stored) {
        assertFalse(message.getSender().isEmpty());
        assertFalse(message.getRecipient().isEmpty());
    }
}
@Test
public void testDeletedMessageDoesNotReload() {

    QuickChat.readStoredMessages();

    Message[] stored = QuickChat.getStoredMessagesArray();

    assertEquals(2, stored.length);

    boolean message2Found = false;
    boolean message5Found = false;

    for (Message message : stored) {
        if (message.getMessage().contains("Where are you?")) {
            message2Found = true;
        }

        if (message.getMessage().equals(
                "Ok, I am leaving without you.")) {
            message5Found = true;
        }
    }

    assertTrue(message2Found);
    assertTrue(message5Found);
}

}