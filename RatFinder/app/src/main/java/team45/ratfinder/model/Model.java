package team45.ratfinder.model;

import java.util.HashMap;

/**
 * Created by laurenyapp on 9/28/17.
 *
 * This is our facade to the Model.  We are allowing
 * access to the model from each controller.
 */

public class Model {

    /** Singleton instance */
    private static final Model _instance = new Model();
    public static Model getInstance() { return _instance; }

    /** Singleton instance */
    private static final HashMap<String, User> registeredUsers = new HashMap<String, User>();
    public static HashMap<String, User> getRegisteredUsersInstance() { return registeredUsers; }

    public User loggedInUser = new User();

    /**
     * This will check to see if the user that is trying to register is a valid user.
     * A valid user is someone who does not already have an account / the email isn't used
     * by another registered user.
     * @param user the user that is attempting to register an account
     * @return returns true if the user was created successfully
     */
    public boolean addUser(User user) {
        if (!registeredUsers.containsKey(user.getUsername())) {
            registeredUsers.put(user.getUsername(), user);
            return true;
        }
        return false;
    }

    /**
     * This will log in a registered user. It will validate username and password.
     * @param username the username in the login attempt
     * @param password the password in the login attempt
     * @return returns true if the user was logged in successfully
     */
    public boolean loginUser(String username, String password) {
        if (registeredUsers.containsKey(username)) {
            User savedUser = registeredUsers.get(username);
            if (password.equals(savedUser.getPassword())) {
                return true;
            }
        }
        return false;
    }

    /**
     * This returns the hashmap of registered users.
     * @return registered users
     */
    public HashMap<String, User> getRegisteredUsers() {
        return registeredUsers;
    }
}
