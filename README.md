# WelcomeToQuickChat

This is my Java console chat application for the PROG5121 (Programming 1A) Portfolio of Evidence at The IIE Rosebank College. It's being built in three parts, and this repo will grow as I complete each one.

## Author

Freddy
Bachelor of Information Technology (Business Systems), First Year
The IIE Rosebank College

## Project Status

Part 1 (Registration and Login) is complete.
Part 2 (Sending Messages) has not been started yet.
Part 3 (Store Data and Display Task Report) has not been started yet.

## Part 1 — Registration and Login

This part handles registering a user and logging them in. It checks:

- That the username contains an underscore and is no more than five characters long
- That the password is at least eight characters long and contains a capital letter, a number, and a special character
- That the cell phone number starts with the South African international code (+27) and is correctly formatted, using a regular expression
- That the login details entered match what was captured during registration

It also includes unit tests for all of the above, written in JUnit 5.

## Technologies Used

- Java 17
- Maven
- JUnit 5

## Project Structure

```
WelcomeToQuickChat/
├── pom.xml
├── src/
│   ├── main/java/
│   │   ├── Login.java              (registration and login logic)
│   │   └── WelcomeToQuickChat.java (console entry point)
│   └── test/java/
│       └── LoginTest.java          (unit tests for Login)
└── README.md
```

## How to Run

1. Clone the repository.
2. Open the project in NetBeans.
3. Run WelcomeToQuickChat.java.
4. Follow the prompts to register, then log in.

## How to Run the Tests

Right-click LoginTest.java in the Projects panel and select Test File. Results show up in the Test Results panel at the bottom.

## Referencing

The regular expression used in checkCellPhoneNumber() was researched and adapted from:

Baeldung (2024) Validate Phone Numbers With Java Regex. Available at: https://www.baeldung.com/java-regex-validate-phone-numbers (Accessed: 8 September 2026).

## Academic Integrity

Submitted as part of the PROG5121 Portfolio of Evidence, in line with The IIE's Assessment Strategy and Policy (IIE009) and Intellectual Integrity Policy (IIE023).
