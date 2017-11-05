package team45.ratfinder.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * RatSighting Class Created by laurenyapp on 10/5/17.
 */

public class RatSighting implements Parcelable{

    private final String uniqueKey;
    private final long createdDate;
    private final String locationType;
    private final int incidentZip;
    private final String incidentAddress;
    private final String city;
    private final String borough;
    private final double latitude;
    private final double longitude;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uniqueKey);
        parcel.writeLong(createdDate);
        parcel.writeString(incidentAddress);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
    }
    public static final Parcelable.Creator<RatSighting> CREATOR
            = new Parcelable.Creator<RatSighting>() {
        public RatSighting createFromParcel(Parcel in) {
            return new RatSighting(in.readString(),in.readLong(),"",0,in.readString(),"","",in.readDouble(), in.readDouble());
        }

        public RatSighting[] newArray(int size) {
            return new RatSighting[size];
        }
    };
}
