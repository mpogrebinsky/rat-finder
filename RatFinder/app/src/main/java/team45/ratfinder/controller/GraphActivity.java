package team45.ratfinder.controller;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import team45.ratfinder.R;
import team45.ratfinder.model.RatSighting;




/**
 * Graph Activity Created by janettanzy on 10/26/17.
 */

public class GraphActivity extends AppCompatActivity {

    //private LinkedList<RatSighting> sightingsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        GraphView graph = (GraphView) findViewById(R.id.graph);
        setSupportActionBar(toolbar);

        Bundle bdl = getIntent().getExtras();
        ArrayList<RatSighting> ratList = bdl.getParcelableArrayList("Data");

        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        String d1 = formatter.format(new Date(ratList.get(0).getCreatedDate()));
        String d2 = formatter.format(new Date(ratList.get(ratList.size()-1).getCreatedDate()));

        HashMap<Integer, Integer> ratMap = monthCounter(ratList);
        DataPoint[] dataPoints = new DataPoint[ratMap.size()];
        int i = 0;
        for (Map.Entry<Integer, Integer> entry : ratMap.entrySet()) {
            DateFormat dfm = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

            try {
                Date d = dfm.parse(entry.getKey()+"/01/2000");
                Log.d("test", d.toString()+"");
                dataPoints[i++] = new DataPoint(d.getTime(), entry.getValue());
            }catch(Exception e) {
                e.printStackTrace();
            }

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
        Log.d("D1DEBUG", new Date(ratList.get(0).getCreatedDate()).toString()+"");
        Log.d("D2DEBUG", new Date(ratList.get(ratList.size()-1).getCreatedDate()).toString()+"");

        DateFormat dfm = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        try {
            Date d = dfm.parse(d1+"/01/2000"); //need the extra date info for complete date, will have to modify this for sorting by year etc
            Log.d("test", d.toString()+"");
            graph.getViewport().setMaxX(d.getTime());
            d = dfm.parse(d2+"/01/2000");
            graph.getViewport().setMinX(d.getTime());

        }catch(Exception e) {
            e.printStackTrace();
        }


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
    private HashMap<Integer, Integer> monthCounter(ArrayList<RatSighting> ratList) {
        HashMap<Integer, Integer> ratMap = new HashMap<>();
        for (RatSighting rat : ratList) {
            //Long month = rat.getCreatedDate();
            //parse this long for month and date - our idea was to divide the long by a number that will get you this info
            //potentially, you will need to convert the longs to dates. The LineGraphSeries might act up otherwise

            SimpleDateFormat formatter = new SimpleDateFormat("MM");
            String d1 = formatter.format(new Date(rat.getCreatedDate()));

            Integer counter = ratMap.getOrDefault(Integer.parseInt(d1), 0);
            ratMap.put(Integer.parseInt(d1), counter + 1);
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
