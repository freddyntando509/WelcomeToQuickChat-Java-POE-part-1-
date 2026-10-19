/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.welcometoquickchat;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
 
/**
 * Message.java
 *
 * This class represents one QuickChat message (Part 2 of the PoE).
 *
 * It creates the message ID and hash, checks the recipient's cell number and
 * the length of the message, lets the user send, disregard or store the
 * message, and keeps track of every message that has been sent.
 *
 * Author: Freddy
 * Module: PROG5121 - Programming 1A
 */
public class Message {
 
    // The longest message allowed. It is kept in one place, so if the rule
    // ever changes we only have to change it here.
    private static final int MAX_MESSAGE_LENGTH = 250;
 
    // "static" means these belong to the Message class as a whole and not to
    // one single message. Every message shares the same total and the same
    // log, which is what we need to count and list all the messages sent.
    private static int totalMessagesSent = 0;
    private static String sentMessagesLog = "";
 
    // The details of this one message
    private String messageID = "";
    private int messageNumber;
    private String recipient = "";
    private String messageText = "";
    private String messageHash = "";
 
    // The name of the file that stored messages are saved in
    private String storageFile = "storedMessages.json";
 
    // A Login object, so we can reuse its cell number check from Part 1
    // instead of writing the same check again
    private Login login = new Login();
 
    // Creates an empty message. The details are filled in with the setters.
    public Message() {
    }
 
    // Creates a message with all its details already set.
    // The unit tests use this one so they can control the message ID.
    public Message(String messageID, int messageNumber, String recipient, String messageText) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
    }
 
    // Setters and getters: they let other classes put values into the
    // private fields above, and read them back out
    public void setMessageNumber(int messageNumber) {
        this.messageNumber = messageNumber;
    }
 
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
 
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
 
    public void setStorageFile(String storageFile) {
        this.storageFile = storageFile;
    }
 
    public String getMessageID() {
        return messageID;
    }
 
    public String getMessageHash() {
        return messageHash;
    }
 
    // Creates a random ten digit message ID.
    // random.nextInt(10) gives a random digit from 0 to 9. The loop does that
    // ten times and adds each digit to the end of the text. The ID is kept as
    // text (not a number) so that a zero at the start is not lost.
    public String generateMessageID() {
        Random random = new Random();
        String newID = "";
 
        for (int i = 0; i < 10; i++) {
            newID = newID + random.nextInt(10);
        }
 
        messageID = newID;
        return "Message ID generated: " + messageID;
    }
 
    // Checks that the message ID is not more than ten characters long
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }
 
    // Checks that the recipient's number is correct, by using the same
    // cell number check we wrote in the Login class for Part 1
    public boolean hasValidRecipient() {
        return login.checkCellPhoneNumber(recipient);
    }
 
    // Returns the message to show the user about the recipient's number:
    // a success message if it is correct, or an error message if it is not
    public String checkRecipientCell() {
        if (hasValidRecipient()) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain "
                + "an international code. Please correct the number and try again.";
    }
 
    // Checks that the message is not longer than 250 characters
    public boolean isMessageLengthValid() {
        return messageText != null && messageText.length() <= MAX_MESSAGE_LENGTH;
    }
 
    // Returns the message to show the user about the length of the message.
    // If it is too long, the error says by how many characters it is too long.
    public String checkMessageLength() {
        if (isMessageLengthValid()) {
            return "Message ready to send.";
        }
        int extraCharacters = messageText.length() - MAX_MESSAGE_LENGTH;
        return "Message exceeds 250 characters by " + extraCharacters
                + "; please reduce the size.";
    }
 
    // Creates the message hash. It is made up of:
    //   the first two digits of the message ID, a colon, the message number,
    //   a colon, and the first and last words of the message,
    // all in capital letters. For example: 00:0:HITONIGHT
    public String createMessageHash() {
        // split(" ") cuts the message into words wherever there is a space
        String[] words = messageText.trim().split(" ");
        String firstWord = keepLettersOnly(words[0]);
        String lastWord = keepLettersOnly(words[words.length - 1]);
 
        // substring(0, 2) gives the first two characters of the message ID
        messageHash = messageID.substring(0, 2) + ":" + messageNumber + ":"
                + firstWord + lastWord;
        messageHash = messageHash.toUpperCase();
        return messageHash;
    }
 
    // Removes anything that is not a letter from a word, so that a word like
    // "tonight?" becomes "tonight". It looks at each character one at a time
    // and only keeps it if it is between a and z, or between A and Z.
    private String keepLettersOnly(String word) {
        String lettersOnly = "";
 
        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);
 
            if ((currentChar >= 'a' && currentChar <= 'z')
                    || (currentChar >= 'A' && currentChar <= 'Z')) {
                lettersOnly = lettersOnly + currentChar;
            }
        }
 
        return lettersOnly;
    }
 
    // Returns all the details of the message in the order the brief asks for:
    // Message ID, Message Hash, Recipient, Message
    public String getMessageDetails() {
        return "Message ID: " + messageID + "\n"
                + "Message Hash: " + messageHash + "\n"
                + "Recipient: " + recipient + "\n"
                + "Message: " + messageText;
    }
 
    // Does what the user chose to do with the message:
    // 1 = send it, 2 = disregard it, 3 = store it to send later
    public String sentMessage(int choice) {
        if (choice == 1) {
            // Count the message and add its details to the log of sent messages
            totalMessagesSent++;
            sentMessagesLog = sentMessagesLog + getMessageDetails() + "\n\n";
            return "Message successfully sent.";
        } else if (choice == 2) {
            return "Press 0 to delete the message.";
        } else if (choice == 3) {
            if (storeMessage()) {
                return "Message successfully stored.";
            }
            return "Message could not be stored.";
        }
        return "Invalid choice. Please choose 1, 2 or 3.";
    }
 
    // Returns the details of every message that has been sent so far
    public static String printMessages() {
        if (totalMessagesSent == 0) {
            return "No messages have been sent.";
        }
        // trim() removes the empty lines left at the end of the log
        return sentMessagesLog.trim();
    }
 
    // Returns how many messages have been sent so far
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }
 
    // Clears the sent messages. The unit tests use this so that every test
    // starts fresh and is not affected by the tests before it.
    public static void resetSentMessages() {
        totalMessagesSent = 0;
        sentMessagesLog = "";
    }
 
    // Saves this message in a JSON file. A JSON file is a text file that
    // stores data in a standard format, so it can be read again later.
    //
    // JSON writing with Gson adapted from:
    // Mkyong (n.d.) Gson - How to convert Java object to / from JSON.
    // Available at: https://mkyong.com/java/how-do-convert-java-object-to-from-json-format-gson-api/
    // (Accessed: 18 October 2026).
    public boolean storeMessage() {
        // Step 1: start with an empty list of stored messages
        JsonArray storedMessages = new JsonArray();
        File file = new File(storageFile);
 
        // Working with files can fail (for example if the file is locked), so
        // the file code is inside try. If something goes wrong, catch shows an
        // error message instead of the program crashing.
        try {
            // Step 2: if the file already has messages in it, read them in first,
            // so the new message is added to them and does not replace them
            if (file.exists() && file.length() > 0) {
                FileReader reader = new FileReader(file);
                storedMessages = JsonParser.parseReader(reader).getAsJsonArray();
                reader.close();
            }
 
            // Step 3: put the details of this message into a JSON object
            JsonObject messageJson = new JsonObject();
            messageJson.addProperty("messageID", messageID);
            messageJson.addProperty("messageHash", messageHash);
            messageJson.addProperty("recipient", recipient);
            messageJson.addProperty("message", messageText);
 
            // Step 4: add this message to the list
            storedMessages.add(messageJson);
 
            // Step 5: write the whole list back to the file.
            // setPrettyPrinting() makes the file neat and easy to read.
            FileWriter writer = new FileWriter(file);
            new GsonBuilder().setPrettyPrinting().create().toJson(storedMessages, writer);
            writer.close();
            return true;
        } catch (IOException e) {
            System.out.println("Error saving the message: " + e.getMessage());
            return false;
        }
    }
}