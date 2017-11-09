package team45.ratfinder.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import team45.ratfinder.R;

public class MainActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private TextView errorMessage;
    private FirebaseAuth mAuth;

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
        Button loginButton = (Button) findViewById(R.id.submit_id);
        Button registerButton = (Button) findViewById(R.id.register);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(username.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("LOGIN STAT", "signInWithEmail:success");
                                    Intent intent = new Intent(MainActivity.this, StartActivity.class);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("LOGIN STAT", "signInWithEmail:failure", task.getException());
                                    FirebaseException e = (FirebaseException)task.getException();
                                    if (e != null) {
                                        Toast.makeText(MainActivity.this, e.getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                        errorMessage.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        });
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}