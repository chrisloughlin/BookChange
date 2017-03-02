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

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText mEmailField;
    private EditText mPasswordField;

    private boolean loggedIn = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        // set up text input
        mEmailField = (EditText) findViewById(R.id.email_edit_text);
        mPasswordField = (EditText) findViewById(R.id.password_edit_text);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("LoginTAG", "onAuthStateChanged:signed_in:" + user.getUid());
                    loggedIn = true;
                } else {
                    // User is signed out
                    Log.d("LoginTAG", "onAuthStateChanged:signed_out");
                    loggedIn = false;
                }
            }
        };

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    // method to validate input
    public boolean validateInput() {
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

        return isValid;
    }

    private void logIn(String email, String password) {
        if (!validateInput()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("LoginTAG", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user
                        if (!task.isSuccessful()) {
                            Log.w("LoginTag", "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void onLoginClicked (View button){
        logIn(mEmailField.getText().toString(), mPasswordField.getText().toString());

        if(loggedIn){
            Intent intent = new Intent(this,MainActivity.class);
            this.startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(this,MainActivity.class);
            this.startActivity(intent);
            finish();
        }
    }

    public void onCreateAccountClicked (View button){
        Intent intent = new Intent(this,CreateAccountActivity.class);
        this.startActivity(intent);
        finish();
    }
}
