/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.welcometoquickchat;

/**
 *
 * @author Student
 */
public class Login {
     // Stored details for the registered user
    public String username;
    public String password;
    public String cellNumber;
    public String firstName;
    public String lastName;
    
     // Remembers registration status and last login result
    public boolean isRegistered;
    public boolean loginSuccessful;
    
    // Setters to store the user's details before registerUser() runs
    public void setUsername(String username) {
        this.username = username;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }
 
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
 
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
     // Checks if the username has an underscore and is 5 characters or less
    public boolean checkUserName(String username) {
        if (username == null) {
            return false;
        }
        return username.contains("_") && username.length() <= 5;
    }
    // Checks if the password meets the complexity rules:
    // at least 8 characters, a capital letter, a number, and a special character
    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
 
        boolean hasCapitalLetter = false;
        boolean hasNumber = false;
        boolean hasSpecialCharacter = false;
 
        for (int i = 0; i < password.length(); i++) {
            char currentChar = password.charAt(i);
 
            if (Character.isUpperCase(currentChar)) {
                hasCapitalLetter = true;
            } else if (Character.isDigit(currentChar)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(currentChar)) {
                hasSpecialCharacter = true;
            }
        }
 
        return hasCapitalLetter && hasNumber && hasSpecialCharacter;
    }
    
    
 




 

     
    }
