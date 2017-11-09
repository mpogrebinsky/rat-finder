package team45.ratfinder.controller;

/**
 * Class created by Lauren on 11/9/17.
 */

public class Model {

    /**
     * This method validates that the user's desired input is of valid length.
     * @param pass the user's desired password / text input
     * @return true if the password is of valid length
     */
    public static boolean validatePassword(String pass) {
        if (pass.length() >= 4) {
            return true;
        }
        return false;
    }

}
