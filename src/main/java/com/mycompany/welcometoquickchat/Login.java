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
 
    // Checks if the cell number starts with +27 and has the correct number
    // of digits (regex based - see reference below).
    // Regex pattern from:
    // Baeldung (2024) Validate Phone Numbers With Java Regex.
    // Available at: https://www.baeldung.com/java-regex-validate-phone-numbers
    // (Accessed: 8 September 2026).
    public boolean checkCellPhoneNumber(String cellNumber) {
        if (cellNumber == null) {
            return false;
        }
 
        String cellNumberRegex = "^\\+27[0-9]{1,10}$";
        return cellNumber.matches(cellNumberRegex);
    }
    
      // Registers the user by checking the username, password, and cell number,
    // and returns a message reporting the result of each check
    public String registerUser() {
        boolean validUsername = checkUserName(username);
        boolean validPassword = checkPasswordComplexity(password);
        boolean validCellNumber = checkCellPhoneNumber(cellNumber);
 
        // Build up the message piece by piece using normal string concatenation
        String message = "";
 
        if (validUsername) {
            message = message + "Username successfully captured.";
        } else {
            message = message + "Username is not correctly formatted; please ensure that "
                    + "your username contains an underscore and is no more than "
                    + "five characters in length.";
        }
        message = message + "\n";
 
        if (validPassword) {
            message = message + "Password successfully captured.";
        } else {
            message = message + "Password is not correctly formatted; please ensure that "
                    + "the password contains at least eight characters, a capital "
                    + "letter, a number, and a special character.";
        }
        message = message + "\n";
 
        if (validCellNumber) {
            message = message + "Cell number successfully captured.";
        } else {
            message = message + "Cell number is incorrectly formatted or does not contain "
                    + "an international code; please correct the number and try again.";
        }
 
        if (validUsername && validPassword && validCellNumber) {
            isRegistered = true;
        }
 
        return message;
    }
    
     // Checks if the entered username and password match the stored details
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        loginSuccessful = isRegistered
                && username != null && username.equals(enteredUsername)
                && password != null && password.equals(enteredPassword);
 
        return loginSuccessful;
    }
    
    // Returns a welcome message if login succeeded, or an error message if it failed
    public String returnLoginStatus() {
        if (loginSuccessful) {
            return "Welcome " + firstName + "," + lastName + " it is great to see you.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }  
}
