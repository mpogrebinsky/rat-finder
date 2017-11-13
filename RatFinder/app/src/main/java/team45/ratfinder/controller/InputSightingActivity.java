package team45.ratfinder.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import team45.ratfinder.R;
import team45.ratfinder.model.RatSighting;

/**
 * Input Sighting Activity Created by Janet on 10/12/17.
 */

public class InputSightingActivity extends AppCompatActivity {


    private EditText addressInput, zipCodeInput, cityInput, locationTypeInput, boroughInput, coordinate1Input, coordinate2Input;
    private DatabaseReference mDatabase;
    //DatabaseReference sightingsListReference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_sighting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button submitButton;
        Button cancelButton;
        cancelButton = (Button) findViewById(R.id.cancelsight_id);
        submitButton = (Button) findViewById(R.id.submit_id);
        addressInput = (EditText) findViewById(R.id.addressInput);
        zipCodeInput = (EditText) findViewById(R.id.zipcodeInput);
        cityInput = (EditText) findViewById(R.id.cityInput);
        locationTypeInput = (EditText) findViewById(R.id.locationTypeInput);
        boroughInput = (EditText) findViewById(R.id.boroughInput);
        coordinate1Input = (EditText) findViewById(R.id.coordinate1Input);
        coordinate2Input = (EditText) findViewById(R.id.coordinate2Input);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("rat-sighting-list");
        cancelButton.setOnClickListener(new View.OnClickListener() {




            public void onClick(View v) {
                Intent intent = new Intent(InputSightingActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(InputSightingActivity.this, StartActivity.class);
                startActivity(intent);
                long time = System.currentTimeMillis();
                long uniqueKey = time / 100000; //temporary uniqueKey generated

                RatSighting temp = new RatSighting(uniqueKey+"", time, locationTypeInput.getText().toString(), Integer.parseInt(zipCodeInput.getText().toString())
                        , addressInput.getText().toString(), cityInput.getText().toString(), boroughInput.getText().toString()
                        , Double.parseDouble(coordinate1Input.getText().toString()), Double.parseDouble(coordinate2Input.getText().toString()));
                         //temporary ignoring time input and just using timestamp, unique key is currently generated by fire base
                mDatabase.push().setValue(FirebaseObjectConverter.sightingToFireBase(temp));

            }
        });
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
            Intent intent = new Intent(InputSightingActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
