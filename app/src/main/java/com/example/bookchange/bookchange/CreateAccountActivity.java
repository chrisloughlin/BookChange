package com.example.bookchange.bookchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by christopher on 2/26/17.
 */

public class CreateAccountActivity extends AppCompatActivity {
    //TODO set up firebase authentication for account creation
    // required for Firebase authentication
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        // initialize instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();
        // set up the listener
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    // go to the main activity when create is clicked--temporary for demo
    public void onCreateClicked (View button){
        Intent intent = new Intent(this,MainActivity.class);
        this.startActivity(intent);
        finish();
    }

    public void onCancelClicked (View button){
        Intent intent = new Intent(this,LoginActivity.class);
        this.startActivity(intent);
        finish();
    }
}
