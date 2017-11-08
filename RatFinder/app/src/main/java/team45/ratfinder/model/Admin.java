package team45.ratfinder.model;

/**
 * Admin Class Created by Lauren on 9/28/17.
 */

public class Admin extends User {

    /**
     * This is the constructor for Administrator of Rat Finder.  The email serves as contact
     * information and as the username.
     *
     * @param username the username that will be used for the admin user
     * @param password the password that will be used for the admin user
     */
    public Admin(String username, String password) {
        super(username, password);
    }

}
