package prog5121part1;

import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class Message {

    private String messageID;
    private String recipient;
    private String message;
    private String messageHash;
    private String sender;
    private int messageNumber;

    // Creates a new message with a unique 10-digit ID.
    public Message(int messageNumber, String recipient, String message) {
        this(messageNumber, recipient, message, "");
    }

    // Creates a new message with the sender's name.
    public Message(int messageNumber, String recipient,
            String message, String sender) {

        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;
        this.sender = sender;

        Random random = new Random();

        long number = 1000000000L
                + (long) (random.nextDouble() * 9000000000L);

        this.messageID = String.valueOf(number);
        this.messageHash = createMessageHash();
    }

    // Loads an existing message from the JSON file.
    public Message(int messageNumber, String messageID,
            String messageHash, String recipient, String message) {

        this(messageNumber, messageID, messageHash,
                recipient, message, "");
    }

    // Loads an existing message, including its sender.
    public Message(int messageNumber, String messageID,
            String messageHash, String recipient,
            String message, String sender) {

        this.messageNumber = messageNumber;
        this.messageID = messageID;
        this.messageHash = messageHash;
        this.recipient = recipient;
        this.message = message;
        this.sender = sender;
    }

    public boolean checkMessageID() {
        return messageID != null
                && messageID.matches("[0-9]{10}");
    }

    public String checkRecipientCell() {
        if (recipient != null
                && recipient.matches("^\\+[0-9]{10,15}$")) {
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or does not contain an international code.";
    }

    public String checkMessageLength() {
        if (message == null) {
            return "Please enter a message of less than 250 characters.";
        }

        if (message.length() > 250) {
            return "Message exceeds 250 characters by "
                    + (message.length() - 250)
                    + "; please reduce the size.";
        }

        return "Message ready to send.";
    }

    // Creates the message hash using the first and last words.
    public String createMessageHash() {
        if (messageID == null || messageID.length() < 2) {
            return "";
        }

        if (message == null || message.trim().isEmpty()) {
            return (messageID.substring(0, 2)
                    + ":" + messageNumber + ":").toUpperCase();
        }

        String[] words = message.trim().split("\\s+");

        String firstWord = words[0];

        String lastWord = words[words.length - 1]
                .replaceAll("[^a-zA-Z0-9]", "");

        return (messageID.substring(0, 2)
                + ":" + messageNumber + ":"
                + firstWord + lastWord).toUpperCase();
    }

    public String getMessageID() {
        return messageID;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getSender() {
        return sender;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public String SentMessage() {
        return "Message successfully sent.";
    }

    // Displays the full details of a message.
    public String printMessages() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + message;
    }

    public int returnTotalMessages() {
        return messageNumber + 1;
    }

    private String escapeJson(String value) {
        if (value == null) {
            return "";
        }

        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    // Stores this message as a stored message in messages.json.
    public String storeMessage() {
        try (FileWriter writer =
                new FileWriter("messages.json", true)) {

            writer.write("{"
                    + "\"messageID\":\"" + escapeJson(messageID) + "\","
                    + "\"messageHash\":\"" + escapeJson(messageHash) + "\","
                    + "\"recipient\":\"" + escapeJson(recipient) + "\","
                    + "\"message\":\"" + escapeJson(message) + "\","
                    + "\"sender\":\"" + escapeJson(sender) + "\","
                    + "\"status\":\"stored\""
                    + "}\n");

            return "Message successfully stored.";

        } catch (IOException e) {
            return "Message could not be stored.";
        }
    }
}