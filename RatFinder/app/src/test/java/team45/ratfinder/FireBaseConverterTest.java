package team45.ratfinder;


import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import team45.ratfinder.controller.FirebaseObjectConverter;
import team45.ratfinder.controller.Model;
import team45.ratfinder.model.RatSighting;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class FireBaseConverterTest {
    @Test
    public void FireBaseIncidentZip() throws Exception {
        Map<String, Object> test = new HashMap<>();
        test.put("Incident Zip", "test");
        test.put("Latitude", 5.0);
        test.put("Longitude", 6.0);
        test.put("Created Date", (long)1234567890.0);
        test.put("City", "TEST CITY");
        test.put("Borough", "TEST BOROUGH");
        RatSighting testRat = FirebaseObjectConverter.getRatSighting(test, "RANDOM");

        assertEquals(testRat.getIncidentZip(), 0);

    }
    @Test
    public void FireBaseLatitudeLong() throws Exception {
        Map<String, Object> test = new HashMap<>();
        test.put("Incident Zip", "test");
        test.put("Latitude", (long)1234.5);
        test.put("Longitude", 6.0);
        test.put("Created Date", (long)1234567890.0);
        test.put("City", "TEST CITY");
        test.put("Borough", "TEST BOROUGH");
        RatSighting testRat = FirebaseObjectConverter.getRatSighting(test, "RANDOM");

        assertEquals(testRat.getLatitude(),1234.5, 1);

    }
    @Test
    public void FireBaseLatitudeDouble() throws Exception {
        Map<String, Object> test = new HashMap<>();
        test.put("Incident Zip", "test");
        test.put("Latitude", (double)1234.5);
        test.put("Longitude", 6.0);
        test.put("Created Date", (long)1234567890.0);
        test.put("City", "TEST CITY");
        test.put("Borough", "TEST BOROUGH");
        RatSighting testRat = FirebaseObjectConverter.getRatSighting(test, "RANDOM");

        assertEquals(testRat.getLatitude(),(double)1234.5, 1);

    }
    @Test
    public void FireBaseLongitudeLong() throws Exception {
        Map<String, Object> test = new HashMap<>();
        test.put("Incident Zip", "test");
        test.put("Latitude", 0.1);
        test.put("Longitude", (long)1234.5);
        test.put("Created Date", (long)1234567890.0);
        test.put("City", "TEST CITY");
        test.put("Borough", "TEST BOROUGH");
        RatSighting testRat = FirebaseObjectConverter.getRatSighting(test, "RANDOM");

        assertEquals(testRat.getLongitude(),1234.5, 1);

    }
    @Test
    public void FireBaseLongitudeDouble() throws Exception {
        Map<String, Object> test = new HashMap<>();
        test.put("Incident Zip", "test");
        test.put("Latitude", 0.1);
        test.put("Longitude", (double)1234.5);
        test.put("Created Date", (long)1234567890.0);
        test.put("City", "TEST CITY");
        test.put("Borough", "TEST BOROUGH");
        RatSighting testRat = FirebaseObjectConverter.getRatSighting(test, "RANDOM");

        assertEquals(testRat.getLongitude(),1234.5, 1);

    }
    @Test
    public void FireBaseIncidentZipCorrect() throws Exception {
        Map<String, Object> test = new HashMap<>();
        test.put("Incident Zip", (long)11010.0);
        test.put("Latitude", 0.1);
        test.put("Longitude", (double)1234.5);
        test.put("Created Date", (long)1234567890.0);
        test.put("City", "TEST CITY");
        test.put("Borough", "TEST BOROUGH");
        RatSighting testRat = FirebaseObjectConverter.getRatSighting(test, "RANDOM");

        assertEquals(testRat.getIncidentZip(),11010);

    }


    /*
    @Test
    public void sightingTest() throws Exception {
        Map<String, Object> test = new HashMap<>();
        test.put("Incident Zip", 50002);
        test.put("Latitude", 5.0);
        test.put("Longitude", 6.0);
        test.put("Created Date", (long)1234567890.0);
        test.put("City", "TEST CITY");
        test.put("Borough", "TEST BOROUGH");
        test.put("Location Type", "Residential");
        test.put("Incident Address", "50 Orchard");
        RatSighting testRat = FirebaseObjectConverter.getRatSighting(test, "RANDOM");


        Map<String, Object> test2 = new HashMap<>();
        test2.put("Incident Zip", 50002);
        test2.put("Latitude", 5.0);
        test2.put("Longitude", 6.0);
        test2.put("Created Date", (long)0000000.0);
        test2.put("City", "TEST CITY");
        test2.put("Borough", "TEST BOROUGH");
        test2.put("Location Type", "Residential");
        test2.put("Incident Address", "50 Orchard");


        Map<String, Object> doop = FirebaseObjectConverter.sightingToFireBase(testRat);
        assertNotEquals(doop, test2);
        assertEquals(doop, test);



    }*/



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