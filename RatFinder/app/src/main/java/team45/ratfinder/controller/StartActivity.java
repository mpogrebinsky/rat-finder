package team45.ratfinder.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import team45.ratfinder.R;
import team45.ratfinder.model.Model;
import team45.ratfinder.model.RatSighting;

/**
 * Created by janettanzy on 9/21/17.
 */

public class StartActivity extends AppCompatActivity{

    ArrayList<RatSighting> sightingsList;
    DatabaseReference mDatabase;
    DatabaseReference sightingsListReference;
    private SimpleRatSightingRecyclerViewAdapter ratSightingRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        sightingsList = new ArrayList<>();
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
        Query sightingsListQuery = sightingsListReference.orderByChild("Latitude").limitToFirst(50);
        sightingsListQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Log.d("Firebase", dataSnapshot.getValue().toString());
                RatSighting ratSighting = FirebaseObjectConverter
                        .getRatSighting((Map)dataSnapshot.getValue(), dataSnapshot.getKey());
                sightingsList.add(ratSighting);
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

        Model model = Model.getInstance();
        ratSightingRecyclerViewAdapter = new SimpleRatSightingRecyclerViewAdapter(sightingsList);
        recyclerView.setAdapter(ratSightingRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * This inner class is our custom adapter.  It takes our basic model information and
     * converts it to the correct layout for this view.
     *
     * In this case, we are just mapping the toString of the Course object to a text field.
     */
    public class SimpleRatSightingRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleRatSightingRecyclerViewAdapter.ViewHolder> {

        /**
         * Collection of the items to be shown in this list.
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
              To edit the actual layout, we would look at: res/layout/course_list_content.xml
              If you look at the example file, you will see it currently just 2 TextView elements
             */
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sightings_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            final Model model = Model.getInstance();
            /*
            This is where we have to bind each data element in the list (given by position parameter)
            to an element in the view (which is one of our two TextView widgets
             */
            //start by getting the element at the correct position
            holder.ratSighting = ratSightings.get(position);
            /*
              Now we bind the data to the widgets.  In this case, pretty simple, put the id in one
              textview and the string rep of a course in the other.
             */
            holder.sightingIdView.setText("" + ratSightings.get(position).getUniqueKey());
            holder.sightingDate.setText(ratSightings.get(position).getCreatedDate());

            /*
             * set up a listener to handle if the user clicks on this list item, what should happen?
             */
            holder.listItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        //on a phone, we need to change windows to the detail view
                        Context context = v.getContext();
                        //create our new intent with the new screen (activity)
                        Intent intent = new Intent(context, DetailActivity.class);
                        /*
                            pass along the id of the course so we can retrieve the correct data in
                            the next window
                         */
                        intent.putExtra("RatSighting Key", holder.ratSighting.getUniqueKey());

                        //model.setCurrentCourse(holder.mCourse);

                        //now just display the new window
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
         * about the binding between the model element (in this case a Course) and the widgets in
         * the list view (in this case the two TextView)
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
