package loginsystem;

/**
 * Represents a user in the system with basic information.
 */
public class User {
    
    // User attributes
    private String userName = "";
    private String password = "";
    private String email = "";
    private String fullName = "";
    private String phoneNumber = "";
    
    /**
     * Constructor to initialize a User object with username and password.
     * @param userName The username of the user.
     * @param password The password of the user.
     */
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    
    /**
     * Getter for the username.
     * @return The username of the user.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter for the username.
     * @param userName The username to set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter for the password.
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the password.
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}