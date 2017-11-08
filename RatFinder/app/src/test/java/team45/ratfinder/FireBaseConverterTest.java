package team45.ratfinder;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import team45.ratfinder.controller.FirebaseObjectConverter;
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
        Map<String, Object> test = new HashMap<String, Object>();
        test.put("Incident Zip", 50002);
        test.put("Latitude", 5.0);
        test.put("Longitude", 6.0);
        test.put("Created Date", (long)1234567890.0);
        test.put("City", "TESTCITY");
        test.put("Borough", "TESTBOROUGH");
        RatSighting doop = FirebaseObjectConverter.getRatSighting(test, "RANDOM");

        assertEquals(doop.getIncidentZip(), 50002);
    }
}