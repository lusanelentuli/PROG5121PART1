/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prog5121part1;

import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    @Test
    public void checkID() {
        Message message = new Message(
                0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertTrue(message.checkMessageID());
        assertEquals(10, message.getMessageID().length());
    }

    @Test
    public void checkHash() {
        Message message = new Message(
                0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        String expectedHash = message.getMessageID().substring(0, 2)
                + ":0:HITONIGHT";
        
        System.out.println("Expected: [" + expectedHash + "]");
        System.out.println("Actual:   [" + message.getMessageHash() + "]");
        System.out.println("Message: [" + message.getMessage() + "]");

        assertEquals(expectedHash, message.getMessageHash());
    }

    @Test
    public void checkContent() {
        Message message = new Message(
                0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertEquals(
                "Hi Mike, can you join us for dinner tonight?",
                message.getMessage()
        );
    }
    @Test
public void checkMessageLengthSuccess() {
    Message message = new Message(
            0,
            "+27718693002",
            "Hi Mike, can you join us for dinner tonight?"
    );

    assertEquals(
            "Message ready to send.",
            message.checkMessageLength()
    );
}

@Test
public void checkMessageLengthFailure() {
    String longMessage = "A".repeat(260);

    Message message = new Message(
            0,
            "+27718693002",
            longMessage
    );

    assertEquals(
            "Message exceeds 250 characters by 10; please reduce the size.",
            message.checkMessageLength()
    );
}
@Test
public void checkRecipientCellSuccess() {
    Message message = new Message(
            0,
            "+27718693002",
            "Hi Mike, can you join us for dinner tonight?"
    );

    assertEquals(
            "Cell phone number successfully captured.",
            message.checkRecipientCell()
    );
}

@Test
public void checkRecipientCellFailure() {
    Message message = new Message(
            0,
            "08575975889",
            "Hi Keegan, did you receive the payment?"
    );

    assertEquals(
            "Cell phone number is incorrectly formatted or does not contain an international code.",
            message.checkRecipientCell()
    );
}
@Test
public void testSentMessage() {
    Message message = new Message(0, "+27718693002", "Hello");
    assertEquals("Message successfully sent.", message.SentMessage());
}

@Test
public void testPrintMessages() {
    Message message = new Message(0, "+27718693002", "Hello");
    String details = message.printMessages();

    assertTrue(details.contains("Message ID:"));
    assertTrue(details.contains("Message Hash:"));
    assertTrue(details.contains("Recipient: +27718693002"));
    assertTrue(details.contains("Message: Hello"));
}
}


   

