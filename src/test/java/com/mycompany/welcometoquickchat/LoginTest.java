/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.welcometoquickchat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * LoginTest.java
 *
 * Unit tests for the Login class, using the test data from the assignment brief.
 * The assertEquals tests check the messages, and the assertTrue/assertFalse
 * tests check the true/false results.
 *
 * Author: Freddy
 * Module: PROG5121 - Programming 1A
 */
public class LoginTest {

    private Login login;

    // Creates a fresh Login object before every test
    @BeforeEach
    public void setUp() {
        login = new Login();
    }
     //Username tests 

    // Registers with the given details and returns the three message lines
    private String[] registerWith(String username, String password, String cellNumber) {
        login.setUsername(username);
        login.setPassword(password);
        login.setCellNumber(cellNumber);
        return login.registerUser().split("\n");
    }

    // ---------- assertEquals tests (messages) ----------

    @Test
    public void testUsernameCapturedMessage() {
        String[] lines = registerWith("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Username successfully captured.", lines[0]);
    }

    @Test
    public void testUsernameNotFormattedMessage() {
        String[] lines = registerWith("kyle!!!!!!", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Username is not correctly formatted; please ensure that your "
                + "username contains an underscore and is no more than five characters "
                + "in length.", lines[0]);
    }

    // Password tests 
 
    @Test
    public void testPasswordCapturedMessage() {
        String[] lines = registerWith("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Password successfully captured.", lines[1]);
    }

    @Test
    public void testPasswordNotFormattedMessage() {
        String[] lines = registerWith("kyl_1", "password", "+27838968976");
        assertEquals("Password is not correctly formatted; please ensure that the "
                + "password contains at least eight characters, a capital letter, "
                + "a number, and a special character.", lines[1]);
    }

    // Cell phone number tests 
 
    @Test
    public void testCellNumberCapturedMessage() {
        String[] lines = registerWith("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Cell number successfully captured.", lines[2]);
    }

    @Test
    public void testCellNumberNotFormattedMessage() {
        String[] lines = registerWith("kyl_1", "Ch&&sec@ke99!", "08966553");
        assertEquals("Cell number is incorrectly formatted or does not contain an "
                + "international code; please correct the number and try again.", lines[2]);
    }

    // ---------- assertTrue / assertFalse tests ----------

    @Test
    public void testLoginSuccessful() {
        registerWith("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginFailed() {
        registerWith("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertFalse(login.loginUser("wrong_", "wrongPassword1!"));
    }

    @Test
    public void testUsernameCorrectlyFormatted() {
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        assertFalse(login.checkUserName("kyle!!!!!!"));
    }

    @Test
    public void testPasswordMeetsComplexity() {
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testPasswordDoesNotMeetComplexity() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    public void testCellNumberCorrectlyFormatted() {
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testCellNumberIncorrectlyFormatted() {
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }

    // ---------- extra tests ----------

    @Test
    public void testRegistrationSucceedsWithValidDetails() {
        registerWith("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.isRegistered());
    }

    @Test
    public void testRegistrationFailsWithInvalidUsername() {
        registerWith("kyle!!!!!!", "Ch&&sec@ke99!", "+27838968976");
        assertFalse(login.isRegistered());
    }

        @Test
    public void testLoginStatusMessageAfterSuccessfulLogin() {
        login.setFirstName("Kyle");
        login.setLastName("Smith");
        registerWith("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        login.loginUser("kyl_1", "Ch&&sec@ke99!");
        assertEquals("Welcome Kyle,Smith it is great to see you.", login.returnLoginStatus());   // <-- change this line
    }
    @Test
    public void testLoginStatusMessageAfterFailedLogin() {
        registerWith("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        login.loginUser("wrong_", "wrongPassword1!");
        assertEquals("Username or password incorrect, please try again.",
                login.returnLoginStatus());
    }
}