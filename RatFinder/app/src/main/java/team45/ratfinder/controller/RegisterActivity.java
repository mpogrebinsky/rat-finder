package team45.ratfinder.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import team45.ratfinder.model.Admin;
import team45.ratfinder.model.Model;
import team45.ratfinder.R;
import team45.ratfinder.model.User;

/**
 * Created by janettanzy on 9/27/17.
 */

public class RegisterActivity extends AppCompatActivity {

    private  Button cancelButton;
    private  EditText username;
    private  EditText password;
    private  CheckBox adminCheckBox;
    private  Button submitButton;
    private  TextView errorMessage;

    private User newUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cancelButton = (Button) findViewById(R.id.register);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        submitButton = (Button) findViewById(R.id.submit_id);
        errorMessage = (TextView) findViewById(R.id.errorMessage);
        adminCheckBox = (CheckBox) findViewById(R.id.admincheckBox);

        final Model model = Model.getInstance();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (adminCheckBox.isChecked()) {
                    newUser = new Admin(username.getText().toString(), password.getText().toString());
                } else {
                    newUser = new User(username.getText().toString(), password.getText().toString());
                }
                if (model.addUser(newUser)) {
                    Intent intent = new Intent(RegisterActivity.this, StartActivity.class);
                    startActivity(intent);
                } else {
                    errorMessage.setVisibility(View.VISIBLE);
                }
            }
        });


    }
}
