package loginsystem;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Manages user registration and data storage.
 */
public class RegisterUser {
    
    // List to store registered users
    private List<User> users;
    
    // File name for storing user data
    static final String FILENAME = "user.txt";
   
    /**
     * Constructor to initialize the RegisterUser object.
     */
    public RegisterUser() {
        this.users = new ArrayList<>();
    }
    
    /**
     * Registers a new user by hashing the password and saving the data to a file.
     * @param username The username of the user.
     * @param password The password of the user.
     * @throws NoSuchAlgorithmException If MD5 algorithm is not available.
     * @throws IOException If an I/O error occurs while saving to file.
     */
    public void register(String username, String password) throws NoSuchAlgorithmException, IOException {
        String hashedPassword = hashPassword(password);
        User newUser = new User(username, hashedPassword);
        users.add(newUser);
        saveToFile(FILENAME);
        System.out.println("User '" + username + "' registered successfully.");
    }

    /**
     * Saves user data to a file in CSV format.
     * @param filename The name of the file to save data.
     * @throws IOException If an I/O error occurs while saving to file.
     */
    public void saveToFile(String filename) throws IOException {
        FileWriter writer = new FileWriter(new File(filename));
        for (User user : users) {
            writer.write(user.getUserName() + "," + user.getPassword() + "\n");
        }
        writer.close();
        System.out.println("User data saved to file.");
    }

    /**
     * Hashes the given password using MD5 algorithm.
     * @param password The password to hash.
     * @return The hashed password.
     * @throws NoSuchAlgorithmException If MD5 algorithm is not available.
     */
    private String hashPassword(String password) throws NoSuchAlgorithmException {
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
     * Checks if the password meets the strength requirements.
     * @param password The password to check.
     * @return True if the password is strong, false otherwise.
     */
    private boolean isStrongPassword(String password) {
        // Password strength rules: minimum length, uppercase, lowercase, numbers, special characters
        int minLength = 8;
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasNumber = false;
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }
        }

        return password.length() >= minLength &&
               hasUppercase && hasLowercase &&
               hasNumber && hasSpecialChar;
    }  
}
