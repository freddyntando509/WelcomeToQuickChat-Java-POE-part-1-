/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.welcometoquickchat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;


/**
 *
 * @author Student
 */
public class LoginTest {
  
    private Login login;
    @BeforeEach
    public void setUp() {
        login = new Login();
    }
     //Username tests 
 
    @Test
    public void testUserNameCorrectlyFormatted() {
        assertEquals(true, login.checkUserName("kyl_1"));
    }
 
    @Test
    public void testUserNameIncorrectlyFormatted() {
        assertEquals(false, login.checkUserName("kyle!!!!!!"));
    }
 
    // Password tests 
 
    @Test
    public void testPasswordMeetsComplexity() {
        assertEquals(true, login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }
 
    @Test
    public void testPasswordDoesNotMeetComplexity() {
        assertEquals(false, login.checkPasswordComplexity("password"));
    }
 
    // Cell phone number tests 
 
    @Test
    public void testCellPhoneNumberCorrectlyFormatted() {
        assertEquals(true, login.checkCellPhoneNumber("+27838968976"));
    }
 
    @Test
    public void testCellPhoneNumberIncorrectlyFormatted() {
        assertEquals(false, login.checkCellPhoneNumber("08966553"));
    }
 
    //  Login tests 
 
    @Test
    public void testLoginSuccessful() {
        login.setUsername("kyl_1");
        login.setPassword("Ch&&sec@ke99!");
        login.setCellNumber("+27838968976");
        login.registerUser();
 
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }
 
    @Test
    public void testLoginFailed() {
        login.setUsername("kyl_1");
        login.setPassword("Ch&&sec@ke99!");
        login.setCellNumber("+27838968976");
        login.registerUser();
 
        assertFalse(login.loginUser("wrong_user", "wrongPassword1!"));
    }
 
    // registerUser() test 
 
    @Test
    public void testRegisterUserSuccessMessage() {
        login.setUsername("kyl_1");
        login.setPassword("Ch&&sec@ke99!");
        login.setCellNumber("+27838968976");
 
        String result = login.registerUser();
 
        assertTrue(result.contains("Username successfully captured."));
        assertTrue(result.contains("Password successfully captured."));
        assertTrue(result.contains("Cell number successfully captured."));
    }
    
}
