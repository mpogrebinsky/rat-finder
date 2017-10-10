package team45.ratfinder.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import team45.ratfinder.R;
import team45.ratfinder.model.RatSighting;

/**
 * Created by janettanzy on 10/5/17.
 */

public class DetailActivity extends AppCompatActivity {

    private RatSighting ratSighting;
    private DatabaseReference ratSightingReference;

    private TextView date;
    private TextView incidentAddress;
    private TextView incidentZip;
    private TextView city;
    private TextView borough;
    private TextView locationType;
    private TextView coordinates;
    private TextView uniqueKey;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        date = (TextView) findViewById(R.id.date);
        incidentAddress = (TextView) findViewById(R.id.incidentAddress);
        incidentZip = (TextView) findViewById(R.id.incidentZip);
        city = (TextView) findViewById(R.id.city);
        borough = (TextView) findViewById(R.id.borough);
        locationType = (TextView) findViewById(R.id.locationType);
        coordinates = (TextView) findViewById(R.id.coordinates);
        uniqueKey = (TextView) findViewById(R.id.uniqueKey);

        final String uniqueKeyStringExtra = getIntent().getStringExtra("RatSighting Key");
        ratSightingReference = FirebaseDatabase.getInstance().getReference("rat-sighting-list/" + uniqueKeyStringExtra);
        ratSightingReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Firebase", dataSnapshot.getValue().toString());
                ratSighting = FirebaseObjectConverter.getRatSighting((Map)dataSnapshot.getValue(), uniqueKeyStringExtra);
                date.setText(ratSighting.getCreatedDate());
                incidentAddress.setText(ratSighting.getIncidentAddress());
                incidentZip.setText("" + ratSighting.getIncidentZip());
                city.setText(ratSighting.getCity());
                borough.setText(ratSighting.getLocationType());
                locationType.setText(ratSighting.getLocationType());
                coordinates.setText("(" + ratSighting.getLatitude() + ", " + ratSighting.getLongitude() + ")");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.on_logout) {
            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
