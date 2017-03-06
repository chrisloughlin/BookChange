package com.example.bookchange.bookchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

/*
This is the main login activity. From here, the user can log in or create an account. An invalid
login will be denied, and the main menu can only be opened with a valid login
 */
public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText mEmailField;
    private EditText mPasswordField;

    /*
    onCreate sets up the text input and sets up the firebase to log in
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        // set up text input
        mEmailField = (EditText) findViewById(R.id.email_edit_text);
        mPasswordField = (EditText) findViewById(R.id.password_edit_text);

        // set up firebase
        FirebaseAuth.getInstance().signOut(); // sign out user when login screen is opened
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("LoginTAG", "onAuthStateChanged:signed_in:" + user.getUid());
                    startMain();
                } else {
                    // User is signed out
                    Log.d("LoginTAG", "onAuthStateChanged:signed_out");
                }
            }
        };

    }

    /*
    Registers the authentication listener
     */
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    /*
    Unegisters the authentication listener
     */
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    // method to validate input for the login info
    public boolean validateInput() {
        boolean isValid = true;

        //ensures the field isnt blank
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

        return isValid;
    }

    /*
    Uses the provided email and password to attempt to log in to the firebase system
     */
    private void logIn(String email, String password) {
        if (!validateInput()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("LoginTAG", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user
                        if (!task.isSuccessful()) {
                            Log.w("LoginTag", "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /*
    Only called once valid input and login are correct
     */
    public void startMain(){
        Intent intent = new Intent(this,MainActivity.class);
        this.startActivity(intent);
        finish();
    }

    /*
    When the login button is clicked, the app attempts to log in to firebase
     */
    public void onLoginClicked (View button){
        logIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
    }

    /*
    Launches an activity to create an account
     */
    public void onCreateAccountClicked (View button){
        Intent intent = new Intent(this,CreateAccountActivity.class);
        this.startActivity(intent);
        finish();
    }
}
