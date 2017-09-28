package team45.ratfinder.model;

/**
 * Created by laurenyapp on 9/28/17.
 */

public class User {

    private String password;
    private String username; //this also serves as the username
    private Boolean accountLocked;

    /**
     * This is the constructor for the normal User of Rat Finder.  The email serves as contact
     * information and as the username.
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
