/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.welcometoquickchat;

import java.io.File;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
 
/**
 * MessageTest.java
 *
 * Unit tests for the Message class, using the test data from the Part 2 brief.
 *
 * Author: Freddy
 * Module: PROG5121 - Programming 1A
 */
public class MessageTest {
 
    // Test data 1 (to be sent) and test data 2 (to be disregarded)
    private static final String RECIPIENT_1 = "+27718693002";
    private static final String MESSAGE_1 = "Hi Mike, can you join us for dinner tonight?";
    private static final String RECIPIENT_2 = "08575975889";
    private static final String MESSAGE_2 = "Hi Keegan, did you receive the payment?";
 
    // Stored messages in the tests go into their own file
    private static final String TEST_FILE = "testStoredMessages.json";
 
    private Message message1;
    private Message message2;
 
    // Runs before every test. It clears the sent messages and creates two fresh
    // messages from the test data, so no test is affected by another test.
    @BeforeEach
    public void setUp() {
        Message.resetSentMessages();
        message1 = new Message("0012345678", 0, RECIPIENT_1, MESSAGE_1);
        message2 = new Message("0198765432", 1, RECIPIENT_2, MESSAGE_2);
        message1.setStorageFile(TEST_FILE);
        message2.setStorageFile(TEST_FILE);
    }
 
    // Runs after every test. It deletes the test JSON file so nothing is left behind.
    @AfterEach
    public void tearDown() {
        new File(TEST_FILE).delete();
    }
 
    // ---------- Message length (success and failure) ----------
 
    @Test
    public void testMessageLengthSuccess() {
        assertEquals("Message ready to send.", message1.checkMessageLength());
    }
 
    @Test
    public void testMessageLengthFailure() {
        // "a".repeat(255) makes a message of 255 letters, which is 5 over the limit
        Message longMessage = new Message("0012345678", 0, RECIPIENT_1, "a".repeat(255));
        assertEquals("Message exceeds 250 characters by 5; please reduce the size.",
                longMessage.checkMessageLength());
    }
 
    // ---------- Recipient number (success and failure) ----------
 
    @Test
    public void testRecipientCorrectlyFormatted() {
        assertEquals("Cell phone number successfully captured.", message1.checkRecipientCell());
    }
 
    @Test
    public void testRecipientIncorrectlyFormatted() {
        assertEquals("Cell phone number is incorrectly formatted or does not contain "
                + "an international code. Please correct the number and try again.",
                message2.checkRecipientCell());
    }
 
    // ---------- Message hash ----------
 
    @Test
    public void testMessageHashTestCase1() {
        assertEquals("00:0:HITONIGHT", message1.createMessageHash());
    }
 
    @Test
    public void testMessageHashesInLoop() {
        // Each message has its own ID and the hash we expect for it.
        // The loop checks every message the same way.
        String[] ids = {"0012345678", "0198765432"};
        String[] messages = {MESSAGE_1, MESSAGE_2};
        String[] expectedHashes = {"00:0:HITONIGHT", "01:1:HIPAYMENT"};
 
        for (int i = 0; i < messages.length; i++) {
            Message message = new Message(ids[i], i, RECIPIENT_1, messages[i]);
            assertEquals(expectedHashes[i], message.createMessageHash());
        }
    }
 
    // ---------- Message ID ----------
 
    @Test
    public void testMessageIDGenerated() {
        Message message = new Message();
        String result = message.generateMessageID();
        assertEquals("Message ID generated: " + message.getMessageID(), result);
    }
 
    @Test
    public void testGeneratedMessageIDIsTenCharacters() {
        Message message = new Message();
        message.generateMessageID();
        assertEquals(10, message.getMessageID().length());
        assertTrue(message.checkMessageID());
    }
 
    @Test
    public void testMessageIDTooLong() {
        Message message = new Message("12345678901", 0, RECIPIENT_1, MESSAGE_1);
        assertFalse(message.checkMessageID());
    }
 
    // ---------- Send, disregard or store ----------
 
    @Test
    public void testSendMessage() {
        assertEquals("Message successfully sent.", message1.sentMessage(1));
    }
 
    @Test
    public void testDisregardMessage() {
        assertEquals("Press 0 to delete the message.", message2.sentMessage(2));
    }
 
    @Test
    public void testStoreMessage() {
        assertEquals("Message successfully stored.", message1.sentMessage(3));
    }
 
    // ---------- Totals and printing ----------
 
    @Test
    public void testTotalMessagesSent() {
        // Only the sent message should be counted, not the disregarded one
        message1.createMessageHash();
        message1.sentMessage(1);  // sent
        message2.sentMessage(2);  // disregarded, so it is not counted
        assertEquals(1, Message.returnTotalMessages());
    }
 
    @Test
    public void testPrintMessagesShowsSentMessage() {
        message1.createMessageHash();
        message1.sentMessage(1);
        String printed = Message.printMessages();
        assertTrue(printed.contains("00:0:HITONIGHT"));
        assertTrue(printed.contains(MESSAGE_1));
    }
 
    @Test
    public void testPrintMessagesWhenNothingSent() {
        assertEquals("No messages have been sent.", Message.printMessages());
    }
 
    // ---------- JSON storage ----------
 
    @Test
    public void testStoreMessageCreatesJsonFile() {
        message1.createMessageHash();
        assertTrue(message1.storeMessage());
        assertTrue(new File(TEST_FILE).exists());
    }
}
 
