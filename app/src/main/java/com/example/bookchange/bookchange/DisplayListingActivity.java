package com.example.bookchange.bookchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by christopher on 2/27/17.
 */

public class DisplayListingActivity extends AppCompatActivity {
    private final String ENTRY_KEY = "ENTRY_ID";
    // firebaseDB variables
    private DatabaseReference mDatabase;
    private String mUserId;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private String courseName;
    private String listingKey;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_listing_activity);
        BookListingDataSource dataSource = new BookListingDataSource(this);
        dataSource.open();
        Long rowId = getIntent().getLongExtra(ENTRY_KEY, 0);
        Log.d("DisplayEntry: ", "" + rowId);
        BookListing entry = dataSource.fetchEntryByIndex(rowId);
        TextView bookTitleTextView = (TextView) findViewById(R.id.bookTitleTextView);
        bookTitleTextView.setText(entry.getBookTitle());
        TextView priceTextView = (TextView) findViewById(R.id.priceTextView);
        priceTextView.setText(""+ entry.getPrice());
        TextView courseNameTextView = (TextView) findViewById(R.id.courseNameTextView);
        courseNameTextView.setText(entry.getClassName());
        TextView accountNameTextView = (TextView) findViewById(R.id.accountNameTextView);
        accountNameTextView.setText(entry.getPosterUsername());

        // Initialize mAuth, mUser, and mDatabase
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // get keys and course names so we can find the listing in the database
        courseName = entry.getClassName();
        listingKey = entry.getId();

        // make sure the user is logged in
        if (mUser == null) {
            // Not logged in, launch LoginActivity
            Intent intent = new Intent(this,LoginActivity.class);
            this.startActivity(intent);
            finish();
        } else {
            mUserId = mUser.getUid(); // get the Uid
        }
    }

    public void onContactClicked(View view){
        finish();
    }
    public void onCloseClicked(View view){
        finish();
    }
    public void onDeleteClicked(View view){
        // remove the listing from the database under both the course and the user
        mDatabase.child("courses").child(courseName).
                child("listings").child(listingKey).removeValue();
        //TODO delete from the user reference
//        mDatabase.child("")
        finish();
    }
}
