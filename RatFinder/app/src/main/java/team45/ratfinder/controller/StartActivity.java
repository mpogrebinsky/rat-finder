package team45.ratfinder.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import team45.ratfinder.R;
import team45.ratfinder.model.RatSighting;

/**
 * Start Activity Created by janettanzy on 9/21/17.
 */

public class StartActivity extends AppCompatActivity{

    private LinkedList<RatSighting> sightingsList;
    private DatabaseReference sightingsListReference;
    private SimpleRatSightingRecyclerViewAdapter ratSightingRecyclerViewAdapter;
    private EditText startDate;
    private EditText endDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        Button editDate = (Button) findViewById(R.id.editDate);
        startDate = (EditText) findViewById(R.id.startDate);
        endDate = (EditText) findViewById(R.id.endDate);
        sightingsList = new LinkedList<>();
        String sortBy = "Created Date";


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        editDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DateFormat dfm = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                try {
                    Date date1 = dfm.parse(startDate.getText().toString());
                    Date date2 = dfm.parse(endDate.getText().toString());
                    Log.d("test", date1.toString()+startDate.getText().toString());
                    Query sightingsListInterval = sightingsListReference.orderByChild("Created Date").startAt(date1.getTime()).endAt(date2.getTime()).limitToLast(1480);
                    sightingsList.clear();
                    sightingsListInterval.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            RatSighting ratSighting = FirebaseObjectConverter
                                    .getRatSighting((Map)dataSnapshot.getValue(), dataSnapshot.getKey());
                            sightingsList.addFirst(ratSighting);
                            ratSightingRecyclerViewAdapter.notifyDataSetChanged();
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

                } catch(Exception e) {
                    e.printStackTrace();
                    Toast.makeText(StartActivity.this, e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    Log.d("HERE", "ERROR");
                }

            }
        });

        sightingsListReference = mDatabase.child("rat-sighting-list");
        //DO NOT DELETE THIS LINE OF CODE:
        //This query will find the first 50 rat sightings and the recycler view will be filled with
        //these. Later, there will be queries based on location, date, etc.
        final Query sightingsListQuery = sightingsListReference.orderByChild(sortBy).limitToLast(30); //currently sorting by date
        sightingsListQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                RatSighting ratSighting = FirebaseObjectConverter
                        .getRatSighting((Map)dataSnapshot.getValue(), dataSnapshot.getKey());
                sightingsList.addFirst(ratSighting);
                ratSightingRecyclerViewAdapter.notifyDataSetChanged();
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
        FloatingActionButton addSightingButton;
        FloatingActionButton mapButton;
        FloatingActionButton graphButton;
        ratSightingRecyclerViewAdapter = new SimpleRatSightingRecyclerViewAdapter(sightingsList);
        recyclerView.setAdapter(ratSightingRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addSightingButton = (FloatingActionButton) findViewById(R.id.addSighting);
        mapButton = (FloatingActionButton) findViewById(R.id.mapButton);
        graphButton = (FloatingActionButton) findViewById(R.id.graphButton);

        addSightingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, InputSightingActivity.class);
                startActivity(intent);
            }
        });

        graphButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, GraphActivity.class);
                //Bundle bundle = new Bundle();
                ArrayList<RatSighting> locs = new ArrayList<>(sightingsList);
                intent.putParcelableArrayListExtra("Data", locs);
                startActivity(intent);
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, MapsActivity.class);
                //Bundle bundle = new Bundle();
                ArrayList<RatSighting> locs = new ArrayList<>(sightingsList);
                intent.putParcelableArrayListExtra("Data", locs);
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
            FirebaseAuth.getInstance().signOut();
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

            //final Model model = Model.getInstance();
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
                sightingIdView = view.findViewById(R.id.sightingID);
                sightingDate = view.findViewById(R.id.sightingDate);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + sightingIdView.getText() + "'";
            }
        }
    }
}
