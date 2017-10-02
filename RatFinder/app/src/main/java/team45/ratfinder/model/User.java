package team45.ratfinder.model;

/**
 * Created by laurenyapp on 9/28/17.
 */

public class User {

    private String password;
    private String username; //this also serves as the username
    private Boolean accountLocked;

    /**
     * This is the constructor for a User of Rat Finder.  The username has to be an email and will
     * serve as the user's contact information.
     * @param username
     * @param password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
        this("user", "pass");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(Boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

}