/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.welcometoquickchat;
import java.util.Scanner;
/**
 *
 * @author Student
 */
public class WelcomeToQuickChat {

    public static void main(String[] args) {
         Scanner scanner = new Scanner(System.in);
        Login login = new Login();
 
        System.out.println("Welcome to QuickChat Registration."); 
        
        // Keep asking until the user registers successfully.
        boolean registered = false;
        while (!registered) {
            System.out.print("Enter a first name: ");
            String firstName = scanner.nextLine();
 
            System.out.print("Enter a last name: ");
            String lastName = scanner.nextLine();
 
            System.out.print("Enter a username (must contain an underscore and be "
                    + "no more than five characters): ");
            String username = scanner.nextLine();
 
            System.out.print("Enter a password (at least eight characters, a capital "
                    + "letter, a number, and a special character): ");
            String password = scanner.nextLine();
 
            System.out.print("Enter a South African cell number (e.g. +27838968976): ");
            String cellNumber = scanner.nextLine();
 
            login.setFirstName(firstName);
            login.setLastName(lastName);
            login.setUsername(username);
            login.setPassword(password);
            login.setCellNumber(cellNumber);
 
            String registrationMessage = login.registerUser();
            System.out.println(registrationMessage);
 
            // Registration only succeeds when all three checks pass.
            registered = registrationMessage.contains("Username successfully captured.")
                    && registrationMessage.contains("Password successfully captured.")
                    && registrationMessage.contains("Cell number successfully captured.");
 
            if (!registered) {
                System.out.println("Please try registering again.\n");
            }
        }
 
        System.out.println("\nRegistration complete. Please log in.");
 
        System.out.print("Enter your username: ");
        String loginUsername = scanner.nextLine();
 
        System.out.print("Enter your password: ");
        String loginPassword = scanner.nextLine();
 
        login.loginUser(loginUsername, loginPassword);
        System.out.println(login.returnLoginStatus());
 
    }
        
 }
      

