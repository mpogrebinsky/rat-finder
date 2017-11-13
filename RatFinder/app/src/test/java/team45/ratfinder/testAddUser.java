package team45.ratfinder;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import team45.ratfinder.model.Model;
import team45.ratfinder.model.User;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Maya Pogrebinsky on 11/5/2017.
 */

public class testAddUser {

    private Model testModel;
    User user1 = new User("Maya", "123");
    User user2 = new User("Billy", "456");
    User user3 = new User("Joe", "789");

    @Before
    public void setUp() {
        testModel = new Model();

        HashMap<String, User> userMap = new HashMap<>();
        userMap.put(user1.getUsername(), user1);
        userMap.put(user2.getUsername(), user2);
        userMap.put(user3.getUsername(), user3);
        testModel.setRegisteredUsers(userMap);
}

    @Test
    public void testAddNonExistingUser() {
        User john = new User("John", "xyz");
        assertTrue(testModel.addUser(john));
        assertEquals(4, testModel.getRegisteredUsers().size());
        assertTrue(testModel.getRegisteredUsers().containsKey(john.getUsername()));
    }

    @Test
    public void testExistingUserSamePass() {
        User maya = new User("Maya", "123");
        assertFalse(testModel.addUser(maya));
        assertEquals(3, testModel.getRegisteredUsers().size());
        assertTrue(testModel.getRegisteredUsers().containsKey(maya.getUsername()));
    }

    @Test
    public void testExistingUserDifferentPass() {
        User maya = new User("Maya", "222");
        assertFalse(testModel.addUser(maya));
        assertEquals(3, testModel.getRegisteredUsers().size());
        assertTrue(user1.getUsername(), testModel.getRegisteredUsers().containsKey(user1.getUsername()));
        assertEquals(user1, testModel.getRegisteredUsers().get(user1.getUsername()));
    }
}
