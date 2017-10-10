package team45.ratfinder.controller;

import java.util.Map;

import team45.ratfinder.model.RatSighting;

/**
 * Created by Maya Pogrebinsky on 10/7/2017.
 */

public class FirebaseObjectConverter {

    static RatSighting getRatSighting(Map<String, Object> map, String uniqueKey) {
        return new RatSighting(uniqueKey,
                (String) map.get("Created Date"),
                (String) map.get("Location Type"),
                (int) ((long) map.get("Incident Zip")),
                (String) map.get("Incident Address"),
                (String)map.get("City"),
                (String) map.get("Borough"),
                (double) map.get("Latitude"),
                (double) map.get("Longitude"));
    }
}
