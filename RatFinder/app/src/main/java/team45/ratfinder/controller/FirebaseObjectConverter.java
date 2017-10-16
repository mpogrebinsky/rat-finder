package team45.ratfinder.controller;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import team45.ratfinder.model.RatSighting;

/**
 * Created by Maya Pogrebinsky on 10/7/2017.
 */

public class FirebaseObjectConverter {
public int test;
    static RatSighting getRatSighting(Map<String, Object> map, String uniqueKey) {
        Log.d("test", map.get("Incident Zip").toString()+ "here");

        int incidentZip = map.get("Incident Zip").toString().equals("") ? 0: ((Long) (map.get("Incident Zip"))).intValue();
        double latitude = map.get("Latitude").toString().equals("") ? 0: ((double) (map.get("Latitude")));
        double longitude = map.get("Longitude").toString().equals("") ? 0: ((double) (map.get("Longitude")));
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
        data.put(("Latitude"), sighting.getLatitude());
        data.put(("Longitude"), sighting.getLongitude());
        return data;



    }
}
