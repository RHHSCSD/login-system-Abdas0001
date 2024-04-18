package loginsystem;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages user login and authentication.
 */
public class LoginSystem {

    // Reference to the RegisterUser instance
    private RegisterUser registerUser;

    /**
     * Constructor to initialize LoginSystem with a RegisterUser instance.
     * @param registerUser The RegisterUser object for user data management.
     */
    public LoginSystem(RegisterUser registerUser) {
        this.registerUser = registerUser;
    }
    
    /**
     * Default constructor for LoginSystem.
     */
    public LoginSystem() {
    }

    /**
     * Validates user login by checking credentials from file.
     * @param username The username for login.
     * @param password The password for login.
     * @return True if login is successful, false otherwise.
     * @throws IOException If an I/O error occurs while reading from file.
     * @throws NoSuchAlgorithmException If MD5 algorithm is not available.
     */
    public static boolean loginFromFile(String username, String password) throws IOException, NoSuchAlgorithmException {
        String hashedPassword = hashPassword(password);

        File f = new File(RegisterUser.FILENAME);
        try (Scanner reader = new Scanner(f)) {
            String line;
            while (reader.hasNextLine()) {
                line = reader.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].equals(username) && parts[1].equals(hashedPassword)) {
                    System.out.println("Login successful.");
                    reader.close();
                    return true;
                }
            }
        }
        System.out.println("Invalid username or password.");
        return false;
    }

    /**
     * Hashes the given password using MD5 algorithm.
     * @param password The password to hash.
     * @return The hashed password.
     * @throws NoSuchAlgorithmException If MD5 algorithm is not available.
     */
    private static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] byteData = md.digest();

        StringBuilder encryptedPassword = new StringBuilder();
        for (byte b : byteData) {
            encryptedPassword.append(String.format("%02x", b & 0xff));
        }
        return encryptedPassword.toString();
    }
    
    /**
     * Main method to test the login functionality.
     * @param args The command-line arguments (not used).
     */
    public static void main(String[] args) {
        
        RegisterUser regSystem = new RegisterUser();
        boolean registerStart = true;
        boolean loginStart = true;

        Scanner s = new Scanner(System.in);
        while (registerStart || loginStart) {
            if (registerStart) {
                System.out.println("For REGISTRETION");
                System.out.println("Username: ");
                String username = s.nextLine();
                System.out.println("Password: ");
                String password = s.nextLine();
                try {
                    regSystem.register(username, password);
                } catch (IOException | NoSuchAlgorithmException ex) {
                    Logger.getLogger(LoginSystem.class.getName()).log(Level.SEVERE, null, ex);
                }
                registerStart = false;
            }
        
            if (loginStart) {
                System.out.println("for LOGIN");
                System.out.println("Username: ");
                String logUserName = s.nextLine();
                System.out.println("Password: ");
                String logPassword = s.nextLine();
                try {
                    boolean logSuccess = loginFromFile(logUserName, logPassword);
                } catch (IOException | NoSuchAlgorithmException ex) {
                    Logger.getLogger(LoginSystem.class.getName()).log(Level.SEVERE, null, ex);
                }
                loginStart = false;
            }
        
            System.out.println("Tell us if you want to register or login");
            String choice = s.nextLine();
            switch (choice) {
                case "register" -> registerStart = true;
                case "login" -> loginStart = true;
                default -> System.out.println("Have a nice day");
            }
        }
        
    }  
}