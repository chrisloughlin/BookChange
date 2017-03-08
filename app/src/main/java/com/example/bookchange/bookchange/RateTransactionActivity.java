package com.example.bookchange.bookchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RatingBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by christopher on 3/8/17.
 */

public class RateTransactionActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private String userId;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_transaction_id);

        // Initialize mAuth, mUser, and mDatabase
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // make sure the user is logged in
        if (mUser == null) {
            // Not logged in, launch LoginActivity
            Intent intent = new Intent(this,LoginActivity.class);
            this.startActivity(intent);
            finish();
        } else {
            userId = mUser.getUid(); // get the Uid
        }
    }

    public void onSubmitRatingClicked(View view){
        RatingBar stars = (RatingBar) findViewById(R.id.stars);
        float rating = stars.getRating();
        //TODO: Something with the rating
        finish();
    }
}
