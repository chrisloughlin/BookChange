package com.example.bookchange.bookchange;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * Created by christopher on 2/26/17.
 */
/*
Attempts to create a new firebase account with the user's inputs. Sends an email
 */
public class CreateAccountActivity extends AppCompatActivity {
    // email and password edittexts
    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mConfirmField;
    private EditText mUsernameField;
    // required for Firebase authentication
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    /*
    Sets up the text as well as required Firebase Authentication components
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_activity);

        // set up text
        mEmailField = (EditText) findViewById(R.id.emailField);
        mUsernameField = (EditText) findViewById(R.id.usernameField);
        mPasswordField = (EditText) findViewById(R.id.passwordField);
        mConfirmField = (EditText) findViewById(R.id.passwordField2);

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
                    if (user != null){
                        String userName = mUsernameField.getText().toString();
                        // set the user's display name
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(userName).build();
                        user.updateProfile(profileUpdates);

                        // send a verification email to the user
                        user.sendEmailVerification();
                        Toast.makeText(CreateAccountActivity.this, "Verification email sent to" + " " + user.getEmail(), Toast.LENGTH_SHORT).show();

                    }
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
                            if (task.isSuccessful()){
                                Log.d("AccountCreateTAG", "Registered!");
                                Toast.makeText(CreateAccountActivity.this,
                                        "Account created!", Toast.LENGTH_SHORT).show();
                            }

                            // If creating the account fails, show a message
                            if (!task.isSuccessful()) {
                                FirebaseException e = (FirebaseException)task.getException();
                                Log.e("AccountCreateTAG", "Failed Registration", e);
                                AlertDialog.Builder errorBuilder = new AlertDialog.Builder
                                        (CreateAccountActivity.this);
                                errorBuilder.setMessage(e.getMessage())
                                        .setTitle("Registration Error!")
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = errorBuilder.create();
                                dialog.show();
                            }
                        }
                    });

            Log.d("OtherTAG", "Got past user creation");
        }
    }

    // no longer used, leaving in for now in case we change it back
//    private void sendVerificationEmail(){
//        Log.d("EmailTAG", "got into the sendVerificationEmail function");
//        final FirebaseUser user = mAuth.getCurrentUser();
//        if(user != null) {
//            Log.d("EmailTAG", "got into the if statement");
//            user.sendEmailVerification()
//                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            Log.d("EmailTAG", "task got completed");
//                            if (task.isSuccessful()) {
//                                Toast.makeText(CreateAccountActivity.this,
//                                        "Verification email sent to" + " " + user.getEmail(),
//                                        Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(CreateAccountActivity.this,
//                                        "Failed to send email.", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//        }
//    }

    // method to validate input
    public boolean validateInput() {
        boolean isValid = true;

        String email = mEmailField.getText().toString();
        if (email.equals("")) {
            mEmailField.setError("Required");
            isValid = false;
        }
        else if(!email.endsWith("@dartmouth.edu")){
            mEmailField.setError("Must be Dartmouth Email");

        } else
            {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        String password2 = mConfirmField.getText().toString();
        if (password.equals("")) {
            mPasswordField.setError("Required");
            isValid = false;
        }

        else if (!password.equals(password2)){
            mPasswordField.setError("Must match");
            mConfirmField.setError("Must match");
            isValid = false;
        }

        else {
            mPasswordField.setError(null);
            mConfirmField.setError(null);
        }

        String userName = mUsernameField.getText().toString();
        if (userName.equals("")) {
            mUsernameField.setError("Required");
            isValid = false;
        } else {
            mUsernameField.setError(null);
        }

        return isValid;
    }

    public void onCreateClicked (View button){
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
        registerAccount(email, password);

        // go to the Login activity
//        Intent intent = new Intent(this,LoginActivity.class);
//        this.startActivity(intent);
//        finish();
    }

    // go back to the loginActivity
    public void onCancelClicked (View button){
        Intent intent = new Intent(this,LoginActivity.class);
        this.startActivity(intent);
        finish();
    }
}
