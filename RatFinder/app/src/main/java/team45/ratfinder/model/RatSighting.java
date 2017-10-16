package team45.ratfinder.model;

import java.util.Date;

/**
 * Created by laurenyapp on 10/5/17.
 */

public class RatSighting {

    private String uniqueKey;
    private long createdDate;
    private String locationType;
    private int incidentZip;
    private String incidentAddress;
    private String city;
    private String borough;
    private double latitude;
    private double longitude;

    /**
     * This is the constructor for creating a new rat sighting object.
     * @param uniqueKey the unique key associated with the sighting
     * @param createdDate the created date associated with the sighting
     * @param locationType the location type associated with the sighting
     * @param incidentZip the incident zip code associated with the sighting
     * @param incidentAddress the incident address associated with the sighting
     * @param city the city associated with the sighting
     * @param borough the borough associated with the sighting
     * @param latitude the latitude associated with the sighting
     * @param longitude the longitude associated with the sighting
     */
    public RatSighting(String uniqueKey, long createdDate, String locationType, int incidentZip,
                       String incidentAddress, String city, String borough, double latitude,
                       double longitude) {
        this.uniqueKey = uniqueKey;
        this.createdDate = createdDate;
        this.locationType = locationType;
        this.incidentZip = incidentZip;
        this.incidentAddress = incidentAddress;
        this.city = city;
        this.borough = borough;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public long getCreatedDate() {
        return createdDate;
    }
    public String getCreatedDateString() {
        return new Date(createdDate).toString();
    }

    public String getLocationType() {
        return locationType;
    }

    public int getIncidentZip() {
        return incidentZip;
    }

    public String getIncidentAddress() {
        return incidentAddress;
    }

    public String getCity() {
        return city;
    }

    public String getBorough() {
        return borough;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String toString() {
        return uniqueKey + "";
    }

}
