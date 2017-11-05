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
import java.util.ArrayList;
import java.util.LinkedList;
import team45.ratfinder.R;
import team45.ratfinder.model.RatSighting;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private LinkedList<RatSighting> sightingsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Bundle bdl = getIntent().getExtras();
        ArrayList<RatSighting> test = bdl.getParcelableArrayList("Data");
        this.sightingsList = new LinkedList<>(test);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     * @param googleMap the reference to google maps
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        for (RatSighting a: sightingsList) {
            LatLng loc = new LatLng(a.getLatitude(), a.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(loc).title(a.getUniqueKey()).snippet(a.getIncidentAddress()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        }
        googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
    }

    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View myContentsView;

        CustomInfoWindowAdapter(){
            myContentsView = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        }

        @Override
        public View getInfoContents(Marker marker) {
            TextView tvTitle = (myContentsView.findViewById(R.id.title));
            tvTitle.setText(marker.getTitle());
            TextView tvSnippet = (myContentsView.findViewById(R.id.snippet));
            tvSnippet.setText(marker.getSnippet());
            return myContentsView;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }
    }
}

