/**package team45.ratfinder.controller;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import team45.ratfinder.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    //@Override
    /**public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}*/
            package team45.ratfinder.controller;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.LinkedList;

import team45.ratfinder.R;
import team45.ratfinder.model.Model;
import team45.ratfinder.model.RatSighting;

/**
 * import demo.cs2340.gatech.edu.mapdemo.R;
 * import demo.cs2340.gatech.edu.mapdemo.model.Location;
 * import demo.cs2340.gatech.edu.mapdemo.model.ModelFacade;
 * import demo.cs2340.gatech.edu.mapdemo.model.Report;
 */

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Model mFacade;
    LinkedList<RatSighting> sightingsList;
    DatabaseReference mDatabase;
    DatabaseReference sightingsListReference;
    private String sortBy = "Created Date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFacade = Model.getInstance();
        Bundle bdl = getIntent().getExtras();
        ArrayList<RatSighting> test = bdl.getParcelableArrayList("Data");
        this.sightingsList = new LinkedList<>(test);
        //Log.d("test", test.get(0).getLatitude()+"");

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Setting a click event handler for the map
        /*mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {



                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);



                // Clears the previously touched position
                // mMap.clear();
                mFacade.addReport("newly added", "Bobs Place", new Location(latLng.latitude, latLng.longitude));

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(mFacade.getLastReport().getName());
                markerOptions.snippet(mFacade.getLastReport().getDescription());

                // Animating to the touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                mMap.addMarker(markerOptions);
            }
        });*/
        for (RatSighting a: sightingsList) {
            LatLng loc = new LatLng(a.getLatitude(), a.getLongitude());
            mMap.addMarker(new MarkerOptions().position(loc).title(a.getUniqueKey()).snippet(a.getIncidentAddress()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        }

        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());


        //final long startDateLongExtra = getIntent().getLongExtra("Start Date");
        //final long endDateLongExtra = getIntent().getLongExtra("End Date");

        //From an intent in the Map Button Click Listener in StartActivity, will take in extras that
        // will provide start and end dates
        //will run a query with start/end dates to make LinkedList of RatSightings


        /*sightingsList = new LinkedList<RatSighting>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        sightingsListReference = mDatabase.child("rat-sighting-list");

        //DO NOT DELETE THIS LINE OF CODE:
        //This query will find the rat sightings by date inputs in the start activity.
        final Query sightingsListQuery = sightingsListReference.orderByChild(sortBy).startAt(startDateLongExtra).endAt(endDateLongExtra);
       final Query sightingsListQuery = sightingsListReference.orderByChild(sortBy).limitToLast(30); //currently sorting by date

        sightingsListQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Log.d("Firebase", dataSnapshot.getValue().toString());
                RatSighting ratSighting = FirebaseObjectConverter
                        .getRatSighting((Map)dataSnapshot.getValue(), dataSnapshot.getKey());
                sightingsList.addFirst(ratSighting);
                //for (RatSighting r : sightingsList) {
                    LatLng loc = new LatLng(ratSighting.getLatitude(), ratSighting.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(loc).title(ratSighting.getUniqueKey()).snippet(ratSighting.getIncidentAddress()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
                //}

                mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //LinkedList<RatSighting> reportList;
        for (RatSighting r : sightingsList) {
            LatLng loc = new LatLng(r.getLatitude(), r.getLongitude());
            mMap.addMarker(new MarkerOptions().position(loc).title(r.getUniqueKey()).snippet(r.getIncidentAddress()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        }

        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
        */
    }

    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View myContentsView;

        CustomInfoWindowAdapter(){
            myContentsView = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        }

        @Override
        public View getInfoContents(Marker marker) {

            TextView tvTitle = ((TextView)myContentsView.findViewById(R.id.title));
            tvTitle.setText(marker.getTitle());
            TextView tvSnippet = ((TextView)myContentsView.findViewById(R.id.snippet));
            tvSnippet.setText(marker.getSnippet());

            return myContentsView;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            // TODO Auto-generated method stub
            return null;
        }

    }

}

