package team45.ratfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static EditText username;
    private static EditText password;
    private static TextView errorMessage;
    private static Button loginButton;
    private static Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        errorMessage = (TextView) findViewById(R.id.errorMessage);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        errorMessage.setVisibility(View.INVISIBLE);
        loginButton = (Button) findViewById(R.id.submit_id);
        registerButton = (Button) findViewById(R.id.cancel);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (username.getText().toString().equals("user") && password.getText().toString().equals("pass")) {
                    Intent intent = new Intent(MainActivity.this, StartActivity.class);
                    startActivity(intent);
                } else {
                    errorMessage.setVisibility(View.VISIBLE);
                }
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                {
                    Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
