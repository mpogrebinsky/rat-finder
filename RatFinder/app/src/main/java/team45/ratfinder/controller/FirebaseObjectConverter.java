package team45.ratfinder.controller;

import java.util.HashMap;
import java.util.Map;

import team45.ratfinder.model.RatSighting;

/**
 * Created by Maya Pogrebinsky on 10/7/2017.
 */

public class FirebaseObjectConverter {
public int test;
    public static RatSighting getRatSighting(Map<String, Object> map, String uniqueKey) {
        int incidentZip = 0;
        try {
            incidentZip = ((Long) (Long.parseLong(map.get("Incident Zip").toString()))).intValue();
        } catch(NumberFormatException e) {
            incidentZip = 0;
        }
        double latitude = 0;
        double longitude = 0;
        if (map.get("Latitude") instanceof Long) {
            latitude = map.get("Latitude").toString().equals("") ? 0: ((long) (map.get("Latitude")));
        } else {
            latitude = map.get("Latitude").toString().equals("") ? 0: ((double) (map.get("Latitude")));
        }

        if (map.get("Longitude") instanceof Long) {
            longitude = map.get("Longitude").toString().equals("") ? 0: ((long) (map.get("Longitude")));
        } else {
            longitude = map.get("Longitude").toString().equals("") ? 0: ((double) (map.get("Longitude")));
        }
        return new RatSighting(uniqueKey,
                (long) map.get("Created Date"),
                (String) map.get("Location Type"),
                incidentZip,
                (String) map.get("Incident Address"),
                (String) map.get("City"),
                (String) map.get("Borough"),
                latitude,
                longitude);
    }
    static Map<String, Object> sightingToFireBase(RatSighting sighting) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(("Created Date"), sighting.getCreatedDate());
        data.put(("Location Type"), sighting.getLocationType());
        data.put(("Incident Zip"), sighting.getIncidentZip());
        data.put(("Incident Address"), sighting.getIncidentAddress());
        data.put(("City"), sighting.getCity());
        data.put(("Borough"), sighting.getBorough());
        data.put(("Latitude"), (double) sighting.getLatitude());
        data.put(("Longitude"), (double) sighting.getLongitude());
        return data;



    }
    /*static Map<String, Object> userToFireBase(User user) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(("username"), user.getUsername());
        data.put(("password"), user.getPassword().hashCode());
        if ()
        data.put(("admin"), );
        return data;
    }
    */
}
