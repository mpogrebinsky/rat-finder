package team45.ratfinder;

import com.google.firebase.auth.FirebaseUser;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import team45.ratfinder.controller.FirebaseObjectConverter;
import team45.ratfinder.controller.Model;
import team45.ratfinder.controller.RegisterActivity;
import team45.ratfinder.model.RatSighting;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class FireBaseConverterTest {
    @Test
    public void FireBaseConverter() throws Exception {
        Map<String, Object> test = new HashMap<>();
        test.put("Incident Zip", 50002);
        test.put("Latitude", 5.0);
        test.put("Longitude", 6.0);
        test.put("Created Date", (long)1234567890.0);
        test.put("City", "TEST CITY");
        test.put("Borough", "TEST BOROUGH");
        RatSighting testRat = FirebaseObjectConverter.getRatSighting(test, "RANDOM");

        assertEquals(testRat.getIncidentZip(), 50002);
    }

    @Test
    public void validatePassword() {
        String pass = "password";
        Boolean testPassword = Model.validatePassword(pass);
        String pass1 = "pass";
        Boolean testPassword1 = Model.validatePassword(pass1);
        String pass2 = "pas";
        Boolean testPassword2 = Model.validatePassword(pass2);
        assertEquals(true, testPassword);
        assertEquals(true, testPassword1);
        assertEquals(false, testPassword2);
    }
}