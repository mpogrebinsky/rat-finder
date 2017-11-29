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

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import team45.ratfinder.R;
public class MainActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private TextView errorMessage;
    private FirebaseAuth mAuth;
    int lockout = 0;
    CallbackManager mCallbackManager;

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
        Button forgot = (Button) findViewById(R.id.forgot);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (lockout == 2) {
                    Toast.makeText(MainActivity.this, "you are locked out!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
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
                                    lockout++;
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
        forgot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                String email = username.getText().toString();
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "email with reset password instructions sent",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "email failed to send",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        });
        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton fbloginButton = findViewById(R.id.fblogin_button);
        fbloginButton.setReadPermissions("email", "public_profile");
        fbloginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("but", "facebook:onSuccess:" + loginResult.getRecentlyDeniedPermissions());
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("nut", "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("goose", "facebook:onError", error);
                // ...
            }
        });



// ...


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("fat", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("nut", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(MainActivity.this, StartActivity.class);
                            startActivity(intent);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("goose", "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

}