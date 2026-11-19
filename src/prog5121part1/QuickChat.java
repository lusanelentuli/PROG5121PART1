package prog5121part1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class QuickChat {

    private static final Scanner input = new Scanner(System.in);

    private static final List<Message> messages = new ArrayList<>();
    private static final List<Message> sentMessages = new ArrayList<>();
    private static final List<Message> disregardedMessages = new ArrayList<>();
    private static final List<Message> storedMessages = new ArrayList<>();

    private static final List<String> messageHashes = new ArrayList<>();
    private static final List<String> messageIDs = new ArrayList<>();

    private static int sentCount = 0;
    private static String senderName;

    // Required Part 3 arrays.
    private static Message[] sentMessagesArray = new Message[0];
    private static Message[] disregardedMessagesArray = new Message[0];
    private static Message[] storedMessagesArray = new Message[0];

    private static String[] messageHashesArray = new String[0];
    private static String[] messageIDsArray = new String[0];

    public static void main(String[] args) {

        System.out.println("Registration");

        System.out.print("Enter your first name: ");
        String firstname = input.nextLine();

        System.out.print("Enter your last name: ");
        String lastname = input.nextLine();

        System.out.print("Enter your username: ");
        String username = input.nextLine();

        System.out.print("Enter your password: ");
        String password = input.nextLine();

        System.out.print("Enter your cell phone number: ");
        String cellphoneNumber = input.nextLine();

        senderName = firstname + " " + lastname;

        Login user = new Login(
                username, password, cellphoneNumber, firstname, lastname
        );

        String registrationMessage = user.registerUser();
        System.out.println(registrationMessage);

        if (!registrationMessage.equals("Registration successful.")) {
            System.out.println("Registration failed. QuickChat closed.");
            return;
        }

        System.out.println("\nLogin");

        System.out.print("Enter your username: ");
        String enteredUsername = input.nextLine();

        System.out.print("Enter your password: ");
        String enteredPassword = input.nextLine();

        if (!user.loginUser(enteredUsername, enteredPassword)) {
            System.out.println(user.returnLoginStatus());
            System.out.println("QuickChat closed.");
            return;
        }

        System.out.println(user.returnLoginStatus());
        System.out.println("\nWelcome to QuickChat.");

        readStoredMessages();

        boolean running = true;

        while (running) {

            System.out.println("\n1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");
            System.out.print("Choose an option: ");

            String choice = input.nextLine().trim();

            switch (choice) {

                case "1":
                    sendMessages();
                    break;

                case "2":
                    displayRecentlySentMessages();
                    break;

                case "3":
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                case "4":
                    storedMessagesMenu();
                    break;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }

        System.out.println("QuickChat closed.");
    }

    // Sends, disregards or stores messages.
    private static void sendMessages() {

        System.out.print("How many messages do you want to enter? ");

        String entered = input.nextLine().trim();
        int count;

        try {
            count = Integer.parseInt(entered);

            if (count < 1) {
                System.out.println("Enter a number greater than zero.");
                return;
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            return;
        }

        for (int i = 0; i < count; i++) {

            System.out.println("\nMessage " + (i + 1));

            System.out.print(
                    "Enter recipient number (include + country code): ");

            String recipient = input.nextLine().trim();

            if (!recipient.matches("^(\\+[0-9]{10,15}|0[0-9]{9})$")) {
                System.out.println("Invalid recipient number.");
                System.out.println("Use + followed by 10 to 15 digits.");
                i--;
                continue;
            }

            System.out.print(
                    "Enter your message (maximum 250 characters): ");

            String text = input.nextLine();

            Message message = new Message(
                    messages.size(), recipient, text, senderName
            );

            if (!message.checkMessageLength().equals(
                    "Message ready to send.")) {

                System.out.println(message.checkMessageLength());
                i--;
                continue;
            }

            System.out.println("\n1) Send Message");
            System.out.println("2) Disregard Message");
            System.out.println("3) Store Message to send later");
            System.out.print("Choose an action: ");

            String action = input.nextLine().trim();
            String status;

            switch (action) {

                case "1":
                    status = "sent";
                    sentMessages.add(message);
                    sentCount++;

                    System.out.println(message.SentMessage());
                    System.out.println("\n" + message.printMessages());
                    break;

                case "2":
                    status = "disregarded";
                    disregardedMessages.add(message);

                    System.out.println("Message disregarded.");
                    break;

                case "3":
                    status = "stored";
                    storedMessages.add(message);

                    System.out.println("Message successfully stored.");
                    break;

                default:
                    status = "disregarded";
                    disregardedMessages.add(message);

                    System.out.println("Invalid action. Message disregarded.");
                    break;
            }

            messages.add(message);
            messageHashes.add(message.getMessageHash());
            messageIDs.add(message.getMessageID());

            refreshArrays();
            saveMessage(message, status);
        }

        System.out.println("\nTotal messages sent: " + sentCount);
    }

    // Displays all recently sent messages.
    private static void displayRecentlySentMessages() {

        System.out.println("\n========== RECENTLY SENT MESSAGES ==========");

        if (sentMessagesArray.length == 0) {
            System.out.println("No sent messages available.");
            return;
        }

        for (Message message : sentMessagesArray) {
            System.out.println(message.printMessages());
            System.out.println("------------------------------------");
        }

        System.out.println("Total messages sent: " + sentMessagesArray.length);
    }

    // Saves a message and its status to the JSON file.
    private static void saveMessage(Message message, String status) {

        try (FileWriter writer =
                new FileWriter("messages.json", true)) {

            writer.write("{"
                    + "\"messageID\":\"" + escape(message.getMessageID()) + "\","
                    + "\"messageHash\":\"" + escape(message.getMessageHash()) + "\","
                    + "\"recipient\":\"" + escape(message.getRecipient()) + "\","
                    + "\"message\":\"" + escape(message.getMessage()) + "\","
                    + "\"sender\":\"" + escape(message.getSender()) + "\","
                    + "\"status\":\"" + escape(status) + "\""
                    + "}\n");

            System.out.println("Message record saved to messages.json.");

        } catch (IOException e) {
            System.out.println("Could not save message: " + e.getMessage());
        }
    }

    // Rewrites the JSON file after a message is deleted.
    private static void updateMessagesFile() {

        try (FileWriter writer =
                new FileWriter("messages.json", false)) {

            for (Message message : messages) {

                String status = "stored";

                if (sentMessages.contains(message)) {
                    status = "sent";
                } else if (disregardedMessages.contains(message)) {
                    status = "disregarded";
                }

                writer.write("{"
                        + "\"messageID\":\"" + escape(message.getMessageID()) + "\","
                        + "\"messageHash\":\"" + escape(message.getMessageHash()) + "\","
                        + "\"recipient\":\"" + escape(message.getRecipient()) + "\","
                        + "\"message\":\"" + escape(message.getMessage()) + "\","
                        + "\"sender\":\"" + escape(message.getSender()) + "\","
                        + "\"status\":\"" + status + "\""
                        + "}\n");
            }

        } catch (IOException e) {
            System.out.println("Could not update messages.json: "
                    + e.getMessage());
        }
    }

    // Escapes special characters before saving JSON.
    private static String escape(String value) {

        if (value == null) {
            return "";
        }

        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    // Reads a string value from one JSON record.
    private static String readJsonValue(String line, String key) {

        String searchKey = "\"" + key + "\"";
        int keyPosition = line.indexOf(searchKey);

        if (keyPosition == -1) {
            return null;
        }

        int colonPosition = line.indexOf(":", keyPosition);

        if (colonPosition == -1) {
            return null;
        }

        int start = colonPosition + 1;

        while (start < line.length()
                && Character.isWhitespace(line.charAt(start))) {
            start++;
        }

        if (start >= line.length() || line.charAt(start) != '"') {
            return null;
        }

        start++;

        StringBuilder result = new StringBuilder();
        boolean escaped = false;

        for (int i = start; i < line.length(); i++) {

            char current = line.charAt(i);

            if (escaped) {

                switch (current) {
                    case 'n':
                        result.append('\n');
                        break;
                    case 'r':
                        result.append('\r');
                        break;
                    case 't':
                        result.append('\t');
                        break;
                    case '"':
                        result.append('"');
                        break;
                    case '\\':
                        result.append('\\');
                        break;
                    case '/':
                        result.append('/');
                        break;
                    default:
                        result.append(current);
                        break;
                }

                escaped = false;

            } else if (current == '\\') {
                escaped = true;

            } else if (current == '"') {
                return result.toString();

            } else {
                result.append(current);
            }
        }

        return null;
    }

    // Loads messages from JSON and populates the required arrays.
    public static void readStoredMessages() {

        messages.clear();
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();

        messageHashes.clear();
        messageIDs.clear();

        sentCount = 0;

        refreshArrays();

        File file = new File("messages.json");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Could not create messages.json.");
                return;
            }
        }

        try (BufferedReader reader =
                new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()
                || line.trim().equals("[")
                || line.trim().equals("]")) {
                continue;
}

                String messageID = readJsonValue(line, "messageID");
                String messageHash = readJsonValue(line, "messageHash");
                String recipient = readJsonValue(line, "recipient");
                String messageText = readJsonValue(line, "message");
                String sender = readJsonValue(line, "sender");
                String status = readJsonValue(line, "status");

                if (messageID == null || messageHash == null
                        || recipient == null || messageText == null
                        || status == null) {

                    System.out.println("A message record could not be read.");
                    continue;
                }

                if (!status.equals("sent")
                        && !status.equals("stored")
                        && !status.equals("disregarded")) {

                    System.out.println("Unknown message status skipped.");
                    continue;
                }

                Message message = new Message(
                        messages.size(),
                        messageID,
                        messageHash,
                        recipient,
                        messageText,
                        sender == null ? "" : sender
                );

                messages.add(message);
                messageHashes.add(messageHash);
                messageIDs.add(messageID);

                if (status.equals("stored")) {
                    storedMessages.add(message);

                } else if (status.equals("sent")) {
                    sentMessages.add(message);
                    sentCount++;

                } else {
                    disregardedMessages.add(message);
                }
            }

            refreshArrays();

            System.out.println("Messages loaded successfully.");
            System.out.println("Sent messages loaded: "
                    + sentMessagesArray.length);
            System.out.println("Disregarded messages loaded: "
                    + disregardedMessagesArray.length);
            System.out.println("Stored messages loaded: "
                    + storedMessagesArray.length);
            System.out.println("Message hashes loaded: "
                    + messageHashesArray.length);
            System.out.println("Message IDs loaded: "
                    + messageIDsArray.length);

        } catch (IOException e) {
            System.out.println("Error reading messages.json: "
                    + e.getMessage());
        }
    }

    // Synchronises the arrays with their corresponding lists.
    private static void refreshArrays() {

        sentMessagesArray = sentMessages.toArray(new Message[0]);

        disregardedMessagesArray =
                disregardedMessages.toArray(new Message[0]);

        storedMessagesArray = storedMessages.toArray(new Message[0]);

        messageHashesArray = messageHashes.toArray(new String[0]);

        messageIDsArray = messageIDs.toArray(new String[0]);
    }
    public static Message[] getSentMessagesArray() {
    return sentMessagesArray.clone();
}

public static Message[] getDisregardedMessagesArray() {
    return disregardedMessagesArray.clone();
}

public static Message[] getStoredMessagesArray() {
    return storedMessagesArray.clone();
}

public static String[] getMessageHashesArray() {
    return messageHashesArray.clone();
}

public static String[] getMessageIDsArray() {
    return messageIDsArray.clone();
}

    // Displays the stored message features.
    private static void storedMessagesMenu() {

        boolean inMenu = true;

        while (inMenu) {

            System.out.println("\n========== STORED MESSAGES ==========");
            System.out.println("1) Display sender and recipient");
            System.out.println("2) Display longest stored message");
            System.out.println("3) Search message using Message ID");
            System.out.println("4) Search messages using recipient");
            System.out.println("5) Delete message using Message Hash");
            System.out.println("6) Display message report");
            System.out.println("7) Return to main menu");

            System.out.print("Choose an option: ");
            String choice = input.nextLine().trim();

            switch (choice) {

                case "1":

                    if (storedMessagesArray.length == 0) {
                        System.out.println("No stored messages available.");
                    } else {
                        for (Message message : storedMessagesArray) {
                            System.out.println("Sender: " + message.getSender());
                            System.out.println("Recipient: "
                                    + message.getRecipient());
                            System.out.println("-------------------------");
                        }
                    }
                    break;

                case "2":
                    System.out.println(displayLongestStoredMessage(
                            Arrays.asList(storedMessagesArray)));
                    break;

                case "3":

                    System.out.print("Enter Message ID: ");
                    String searchID = input.nextLine().trim();

                    System.out.println(searchMessageByID(
                            Arrays.asList(storedMessagesArray), searchID));
                    break;

                case "4":

                    System.out.print("Enter recipient number: ");
                    String searchRecipient = input.nextLine().trim();

                   List<Message> searchableMessages = new ArrayList<>();

                   searchableMessages.addAll(Arrays.asList(sentMessagesArray));
                   searchableMessages.addAll(Arrays.asList(storedMessagesArray));

                    System.out.println(searchMessagesByRecipient(
                            searchableMessages, searchRecipient));
                    break;

                case "5":

                    System.out.print("Enter Message Hash: ");
                    String deleteHash = input.nextLine().trim();

                    boolean deleted = false;

                    for (int i = 0; i < storedMessages.size(); i++) {

                        Message message = storedMessages.get(i);

                        if (message.getMessageHash().equals(deleteHash)) {

                            storedMessages.remove(i);
                            messages.remove(message);

                            messageHashes.remove(message.getMessageHash());
                            messageIDs.remove(message.getMessageID());

                            updateMessagesFile();
                            refreshArrays();

                            System.out.println("Message: \""
                                    + message.getMessage()
                                    + "\" successfully deleted.");

                            deleted = true;
                            break;
                        }
                    }

                    if (!deleted) {
                        System.out.println("Message Hash not found.");
                    }
                    break;

                case "6":
                    refreshArrays();
                    displayMessageReport();
                    break;

                case "7":
                    inMenu = false;
                    System.out.println("Returning to main menu.");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    // Displays the full report of sent and stored messages.
    public static void displayMessageReport() {

        System.out.println("\n========== MESSAGE REPORT ==========");

        System.out.println("\nSENT MESSAGES:");

        if (sentMessagesArray.length == 0) {
            System.out.println("No sent messages available.");
        } else {
            for (Message message : sentMessagesArray) {
                printReportDetails(message);
            }
        }

        System.out.println("\nSTORED MESSAGES:");

        if (storedMessagesArray.length == 0) {
            System.out.println("No stored messages available.");
        } else {
            for (Message message : storedMessagesArray) {
                printReportDetails(message);
            }
        }
    }

    // Prints the required message report details.
    public static void printReportDetails(Message message) {

        System.out.println("Message Hash: " + message.getMessageHash());
        System.out.println("Recipient: " + message.getRecipient());
        System.out.println("Message: " + message.getMessage());
        System.out.println("------------------------------------");
    }

    // Searches for a message using its ID.
    public static String searchMessageByID(
            List<Message> messageList, String messageID) {

        for (Message message : messageList) {
            if (message.getMessageID().equals(messageID)) {
                return message.printMessages();
            }
        }

        return "Message not found.";
    }

    // Searches for messages sent to a particular recipient.
    public static String searchMessagesByRecipient(
            List<Message> messageList, String recipient) {

        StringBuilder results = new StringBuilder();

        for (Message message : messageList) {
            if (message.getRecipient().equals(recipient)) {
                results.append(message.printMessages())
                        .append("\n\n");
            }
        }

        if (results.length() == 0) {
            return "No messages found for this recipient.";
        }

        return results.toString();
    }

    // Deletes a message using its hash.
    public static String deleteMessageByHash(
            List<Message> messageList, String messageHash) {

        for (int i = 0; i < messageList.size(); i++) {

            Message message = messageList.get(i);

            if (message.getMessageHash().equals(messageHash)) {
                messageList.remove(i);
                return "Message successfully deleted.";
            }
        }

        return "Message not found.";
    }

    // Finds and displays the longest stored message.
    public static String displayLongestStoredMessage(
            List<Message> storedMessageList) {

        if (storedMessageList.isEmpty()) {
            return "No stored messages available.";
        }

        Message longest = storedMessageList.get(0);

        for (Message message : storedMessageList) {
            if (message.getMessage().length()
                    > longest.getMessage().length()) {
                longest = message;
            }
        }

        return longest.printMessages();
    }
}