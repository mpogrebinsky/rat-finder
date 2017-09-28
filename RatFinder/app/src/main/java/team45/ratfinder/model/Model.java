package team45.ratfinder.model;

import java.util.HashMap;

/**
 * Created by laurenyapp on 9/28/17.
 */

public class Model {

    /** Singleton instance */
    private static final Model _instance = new Model();
    public static Model getInstance() { return _instance; }

    /** Singleton instance */
    private static final HashMap<String, User> registeredUsers = new HashMap<String, User>();
    public static HashMap<String, User> getRegisteredUsersInstance() { return registeredUsers; }

    public User loggedInUser = new User();

    public boolean addUser(User user) {
        if (!registeredUsers.containsKey(user.getUsername())) {
            registeredUsers.put(user.getUsername(), user);
            return true;
        }
        return false;
    }

    public boolean loginUser(String username, String password) {
        if (registeredUsers.containsKey(username)) {
            User savedUser = registeredUsers.get(username);
            if (password.equals(savedUser.getPassword())) {
                return true;
            }
        }
        return false;
    }

    public HashMap<String, User> getRegisteredUsers() {
        return registeredUsers;
    }
}
