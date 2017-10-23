package team45.ratfinder.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import team45.ratfinder.R;
import team45.ratfinder.model.Model;
import team45.ratfinder.model.RatSighting;

/**
 * Created by janettanzy on 9/21/17.
 */

public class StartActivity extends AppCompatActivity{

    LinkedList<RatSighting> sightingsList;
    DatabaseReference mDatabase;
    DatabaseReference sightingsListReference;
    private SimpleRatSightingRecyclerViewAdapter ratSightingRecyclerViewAdapter;
    private String sortBy = "Created Date";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        sightingsList = new LinkedList<RatSighting>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
       /* DatabaseReference testReference = mDatabase.child("991");
        testReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Firebase", dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        sightingsListReference = mDatabase.child("rat-sighting-list");

        //DO NOT DELETE THIS LINE OF CODE:
        //This query will find the first 50 rat sightings and the recycler view will be filled with
        //these. Later, there will be queries based on location, date, etc.
        final Query sightingsListQuery = sightingsListReference.orderByChild(sortBy).limitToLast(30); //currently sorting by date
        sightingsListQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Log.d("Firebase", dataSnapshot.getValue().toString());
                RatSighting ratSighting = FirebaseObjectConverter
                        .getRatSighting((Map)dataSnapshot.getValue(), dataSnapshot.getKey());
                sightingsList.addFirst(ratSighting);
                ratSightingRecyclerViewAdapter.notifyDataSetChanged();

                /*current indexing rules on firebase("must be updated for performance reasons everytime csv data is rearranged"
                "rules": {
    "rat-sighting-list": {
      ".indexOn": ["Created Date","Latitude", "Longitude", "Incident Zip", "Incident Address", "City", "Borough", "Location Type"],
    ".read": true,
    ".write": true
    }
  }
                 */

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

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.sightings_list);

        Model model = Model.getInstance();
        FloatingActionButton addSightingButton;
        FloatingActionButton mapButton;
       // Collections.reverse(sightingsList);
        ratSightingRecyclerViewAdapter = new SimpleRatSightingRecyclerViewAdapter(sightingsList);
        recyclerView.setAdapter(ratSightingRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addSightingButton = (FloatingActionButton) findViewById(R.id.addSighting);
        mapButton = (FloatingActionButton) findViewById(R.id.mapButton);

        addSightingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, InputSightingActivity.class);
                startActivity(intent);
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, MapsActivity.class);

                //intent.putExtra("Start Date", );
                //intent.putExtra("End Date", )
                startActivity(intent);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.on_logout) {
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * This inner class is our custom recycler view adapter.  It takes our rat sighting information
     * and converts it to the correct layout for this view. The created date and unique key will
     * be the information needed for the recycler view.
     *
     */
    public class SimpleRatSightingRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleRatSightingRecyclerViewAdapter.ViewHolder> {

        /**
         * Our list of rat sightings from the database
         */
        private final List<RatSighting> ratSightings;

        /**
         * set the items to be used by the adapter
         * @param items the list of items to be displayed in the recycler view
         */
        public SimpleRatSightingRecyclerViewAdapter(List<RatSighting> items) {
            ratSightings = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            /*

              This sets up the view for each individual item in the recycler display

             */
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sightings_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            final Model model = Model.getInstance();
            holder.ratSighting = ratSightings.get(position);
            holder.sightingIdView.setText("" + ratSightings.get(position).getUniqueKey());
            holder.sightingDate.setText(ratSightings.get(position).getCreatedDateString());


            holder.listItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        Context context = v.getContext();
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("RatSighting Key", holder.ratSighting.getUniqueKey());

                        //model.setCurrentCourse(holder.mCourse);

                        context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return ratSightings.size();
        }

        /**
         * This inner class represents a ViewHolder which provides us a way to cache information
         * about the binding between the rat sightings and the 2 information holders presented
         * in the view
         */

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View listItemView;
            public final TextView sightingIdView;
            public final TextView sightingDate;
            public RatSighting ratSighting;

            public ViewHolder(View view) {
                super(view);
                listItemView = view;
                sightingIdView = (TextView) view.findViewById(R.id.sightingID);
                sightingDate = (TextView) view.findViewById(R.id.sightingDate);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + sightingIdView.getText() + "'";
            }
        }
    }
}
