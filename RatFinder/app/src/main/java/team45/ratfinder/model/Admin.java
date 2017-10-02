package team45.ratfinder.model;

/**
 * Created by laurenyapp on 9/28/17.
 */

public class Admin extends User {

    /**
     * This is the constructor for Administrator of Rat Finder.  The email serves as contact
     * information and as the username.
     *
     * @param username
     * @param password
     */
    public Admin(String username, String password) {
        super(username, password);
    }

}
