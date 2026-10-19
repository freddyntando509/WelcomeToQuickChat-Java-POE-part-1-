/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.welcometoquickchat;
import java.util.Scanner;
/**
 *
 * @author Student
 */
 
 //The user registers and logs in (Part 1), and can then use the menu
 //to send messages (Part 2). This class only talks to the user. The
 //checking and the rules live in the Login and Message classes.
 
public class WelcomeToQuickChat {
 
    // The program starts here. Each step is in its own method below, which
    // keeps main() short and easy to follow.
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();
 
        registerNewUser(scanner, login);
        logInUser(scanner, login);
 
        // This is only reached once the user has logged in successfully,
        // so only logged in users can send messages
        System.out.println("\nWelcome to QuickChat.");
        int maxMessages = askHowManyMessages(scanner);
        runMenu(scanner, maxMessages);
 
        scanner.close();
    }
 
    // Asks for the registration details again and again until the Login
    // class says the registration worked
    private static void registerNewUser(Scanner scanner, Login login) {
        System.out.println("--- QuickChat Registration ---");
 
        while (!login.isRegistered()) {
            System.out.print("Enter a first name: ");
            login.setFirstName(scanner.nextLine());
 
            System.out.print("Enter a last name: ");
            login.setLastName(scanner.nextLine());
 
            System.out.print("Enter a username (underscore, no more than five characters): ");
            login.setUsername(scanner.nextLine());
 
            System.out.print("Enter a password (eight characters, a capital letter, "
                    + "a number and a special character): ");
            login.setPassword(scanner.nextLine());
 
            System.out.print("Enter a South African cell number (e.g. +27838968976): ");
            login.setCellNumber(scanner.nextLine());
 
            // registerUser() checks everything and returns a message for each check
            System.out.println(login.registerUser());
 
            if (!login.isRegistered()) {
                System.out.println("Please try registering again.\n");
            }
        }
    }
 
    // Asks for the login details again and again until they are correct
    private static void logInUser(Scanner scanner, Login login) {
        System.out.println("\n--- QuickChat Login ---");
        boolean loggedIn = false;
 
        while (!loggedIn) {
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
 
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();
 
            loggedIn = login.loginUser(username, password);
            System.out.println(login.returnLoginStatus());
        }
    }
 
    // Asks how many messages the user wants to enter. It keeps asking until
    // the user types a whole number that is 1 or more.
    public static int askHowManyMessages(Scanner scanner) {
        int numMessages = 0;
 
        while (numMessages < 1) {
            System.out.print("How many messages would you like to enter? ");
 
            // hasNextInt() is true if the user typed a whole number
            if (scanner.hasNextInt()) {
                numMessages = scanner.nextInt();
            }
            // Clears the rest of the line. This also throws away anything
            // that was not a number, so the program does not crash.
            scanner.nextLine();
 
            if (numMessages < 1) {
                System.out.println("Please enter a whole number of 1 or more.");
            }
        }
 
        return numMessages;
    }
 
    // Shows the menu again and again until the user chooses to quit.
    // This is the while loop that keeps the application running.
    public static void runMenu(Scanner scanner, int maxMessages) {
        boolean messagesEntered = false;
        boolean quit = false;
 
        while (!quit) {
            System.out.println("\n1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option (1-3): ");
 
            // The choice is read as text, so typing a letter cannot crash the program
            String option = scanner.nextLine();
 
            if (option.equals("1")) {
                // The user may only enter the number of messages they chose at the start
                if (messagesEntered) {
                    System.out.println("You have already entered your "
                            + maxMessages + " messages.");
                } else {
                    sendMessages(scanner, maxMessages);
                    messagesEntered = true;
                }
            } else if (option.equals("2")) {
                System.out.println("Coming Soon.");
            } else if (option.equals("3")) {
                quit = true;
                System.out.println("Thank you for using QuickChat. Goodbye!");
            } else {
                System.out.println("Invalid option. Please enter 1, 2 or 3.");
            }
        }
    }
 
    // Lets the user enter the number of messages they chose at the start.
    // The for loop runs once for each message, and i is the message number.
    private static void sendMessages(Scanner scanner, int maxMessages) {
        for (int i = 0; i < maxMessages; i++) {
            System.out.println("\n--- Message " + (i + 1) + " of " + maxMessages + " ---");
 
            Message message = createMessage(scanner, i);
            handleMessage(scanner, message);
        }
 
        // After all the messages, show the ones that were sent and how many there were
        System.out.println("\nMessages sent:");
        System.out.println(Message.printMessages());
        System.out.println("Total messages sent: " + Message.returnTotalMessages());
    }
 
    // Asks for the recipient and the message, and creates the ID and the hash
    private static Message createMessage(Scanner scanner, int messageNumber) {
        Message message = new Message();
        message.setMessageNumber(messageNumber);
        System.out.println(message.generateMessageID());
 
        // A do-while loop runs the code once first and then repeats it while the
        // recipient is not valid, so the user must enter a correct number
        do {
            System.out.print("Enter the recipient's cell number (e.g. +27718693002): ");
            message.setRecipient(scanner.nextLine());
            System.out.println(message.checkRecipientCell());
        } while (!message.hasValidRecipient());
 
        // The same idea for the message: repeat until it is 250 characters or less
        do {
            System.out.print("Enter your message (250 characters at most): ");
            message.setMessageText(scanner.nextLine());
            System.out.println(message.checkMessageLength());
        } while (!message.isMessageLengthValid());
 
        // The hash needs the message text, so it is created last
        message.createMessageHash();
        return message;
    }
 
    // Asks if the message should be sent, disregarded or stored, and does it
    private static void handleMessage(Scanner scanner, Message message) {
        int choice = askSendChoice(scanner);
        String result = message.sentMessage(choice);
        System.out.println(result);
 
        if (choice == 1) {
            // The full details are shown once the message has been sent
            System.out.println(message.getMessageDetails());
        } else if (choice == 2) {
            // The message is only deleted once the user presses 0
            String key = scanner.nextLine();
            while (!key.equals("0")) {
                System.out.println(result);
                key = scanner.nextLine();
            }
            System.out.println("Message deleted.");
        }
    }
 
    // Keeps asking until the user chooses 1, 2 or 3, then returns that number
    private static int askSendChoice(Scanner scanner) {
        String choice = "";
 
        while (!choice.equals("1") && !choice.equals("2") && !choice.equals("3")) {
            System.out.println("\nWhat would you like to do with this message?");
            System.out.println("1) Send Message");
            System.out.println("2) Disregard Message");
            System.out.println("3) Store Message to send later");
            System.out.print("Choose an option (1-3): ");
            choice = scanner.nextLine();
        }
 
        // The choice can only be 1, 2 or 3 here, so it is safe to turn it into a number
        return Integer.parseInt(choice);
    }
}
      

