package team45.ratfinder.controller;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import team45.ratfinder.R;
import team45.ratfinder.model.RatSighting;


/**
 * Created by janettanzy on 10/26/17.
 */

public class GraphActivity extends AppCompatActivity {

    //private LinkedList<RatSighting> sightingsList;

    private GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        graph = (GraphView) findViewById(R.id.graph);
        setSupportActionBar(toolbar);

        Bundle bdl = getIntent().getExtras();
        ArrayList<RatSighting> ratList = bdl.getParcelableArrayList("Data");


        // generate Dates
        /*Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();*/

        HashMap<Long, Integer> ratMap = monthCounter(ratList);
        DataPoint[] dataPoints = new DataPoint[ratMap.size()];
        int i = 0;
        for (Map.Entry<Long, Integer> entry : ratMap.entrySet()) {
            dataPoints[i++] = new DataPoint(entry.getKey(), entry.getValue());
        }

        // you can directly pass Date objects to DataPoint-Constructor
        // this will convert the Date to double via Date#getTime()
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);

        //GraphView graph = (GraphView) findViewById(graph);




        graph.addSeries(series);

// set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(GraphActivity.this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

// set manual x bounds to have nice steps
        //d1 and d3 need to be changed to be months that are included in the graph
        graph.getViewport().setMinX(d1.getTime());
        graph.getViewport().setMaxX(d3.getTime());
        graph.getViewport().setXAxisBoundsManual(true);

// as we use dates as labels, the human rounding to nice readable numbers
// is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);
    }

    /**
     * This method creates the hash map of rat sightings per month
     * @param ratList list of all of the RatSightings from the Start Activity
     * @return a hash map with keys as the different months with the values being the # of sightings in that month
     */
    public HashMap<Long, Integer> monthCounter(ArrayList<RatSighting> ratList) {
        HashMap<Long, Integer> ratMap = new HashMap<>();
        for (RatSighting rat : ratList) {
            Long month = rat.getCreatedDate();
            //parse this long for month and date - our idea was to divide the long by a number that will get you this info
            //potentially, you will need to convert the longs to dates. The LineGraphSeries might act up otherwise
            Integer counter = ratMap.getOrDefault(month, 0);
            ratMap.put(month, counter + 1);
        }
        return ratMap;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // handles when the user clicks on one rat sighting in particular
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.on_logout) {
            Intent intent = new Intent(GraphActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
