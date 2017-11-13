package team45.ratfinder.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;
import team45.ratfinder.R;
import team45.ratfinder.model.Admin;
import team45.ratfinder.model.User;

/**
 * Register Activity Created by Janet on 9/27/17.
 */

public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private CheckBox adminCheckBox;
    private FirebaseAuth mAuth;
    private User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button cancelButton = (Button) findViewById(R.id.register);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        Button submitButton = (Button) findViewById(R.id.submit_id);
        adminCheckBox = (CheckBox) findViewById(R.id.admincheckBox);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                if (adminCheckBox.isChecked()) {
                    newUser = new Admin(username.getText().toString(), password.getText().toString());
                } else {
                    newUser = new User(username.getText().toString(), password.getText().toString());
                }

               createUser(newUser.getUsername(), newUser.getPassword(), adminCheckBox.isChecked());
            }
        });


    }
    private void createUser(String email, String password, boolean admin) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("LOG IN STATUS", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            onCreationSuccess(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("LOG IN STATUS", "createUserWithEmail:failure", task.getException());
                            FirebaseException e = (FirebaseException)task.getException();
                            if (e != null) {
                                Toast.makeText(RegisterActivity.this, e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }
    private void onCreationSuccess(FirebaseUser user) {
        //this is to set an admin flag for the account in case a distinction is later needed.
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        Map<String, Object> temp = new HashMap<>();
        temp.put("admin", adminCheckBox.isChecked() ? 1 : 0);
        temp.put("password", "REDACTED");
        Log.d("test", user.getEmail());
        mDatabase.child(cleanEmail(user.getEmail())).setValue(temp);
        Intent intent = new Intent(RegisterActivity.this, StartActivity.class);
        startActivity(intent);

    }
    private String cleanEmail(String email) {
        //Fire Base can't add a key with the following characters present directly so email must be
        //cleaned
        email = email.replace(".", "");
        email = email.replace("#", "");
        email = email.replace("$", "");
        email = email.replace("[", "");
        email = email.replace("]", "");
        return email;

    }
}
