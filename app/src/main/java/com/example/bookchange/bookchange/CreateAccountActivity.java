package com.example.bookchange.bookchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by christopher on 2/26/17.
 */

public class CreateAccountActivity extends AppCompatActivity {
    // email and password edittexts
    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mUsernameField;

    // required for Firebase authentication
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        // set up text
        mEmailField = (EditText) findViewById(R.id.emailField);
        mUsernameField = (EditText) findViewById(R.id.usernameField);
        mPasswordField = (EditText) findViewById(R.id.passwordField);

        // initialize instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();
        // set up the listener
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    // User is signed in
                    // set up (Should we have a global with the Uid?)
                } else {
                    // User is signed out
                }
            }
        };
    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if (mAuthStateListener != null){
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    // method that registers the account
    public void registerAccount(String email, String password){
        // if the input is invalid, return
        if(!validateInput()){
            return;
        }
        else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // If creating the account fails, show a message
                            if (!task.isSuccessful()) {
                                Toast.makeText(CreateAccountActivity.this, "Authentication failed!"
                                        , Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void sendVerificationEmail(){
        final FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(CreateAccountActivity.this,
                                        "Verification email sent to" + " " + user.getEmail(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(CreateAccountActivity.this,
                                        "Failed to send email.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    // method to validate input
    private boolean validateInput() {
        boolean isValid = true;

        String email = mEmailField.getText().toString();
        if (email.equals("")) {
            mEmailField.setError("Required");
            isValid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (password.equals("")) {
            mPasswordField.setError("Required");
            isValid = false;
        } else {
            mPasswordField.setError(null);
        }

        // don't worry about the username yet

        return isValid;
    }

    // go to the main activity when create is clicked--temporary for demo
    public void onCreateClicked (View button){
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
        registerAccount(email, password);
        sendVerificationEmail();
    }

    // go back to the loginActivity
    public void onCancelClicked (View button){
        Intent intent = new Intent(this,LoginActivity.class);
        this.startActivity(intent);
        finish();
    }
}
