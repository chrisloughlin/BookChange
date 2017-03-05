package com.example.bookchange.bookchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by christopher on 2/27/17.
 */

public class DisplayListingActivity extends AppCompatActivity {
    private final String ENTRY_KEY = "ENTRY_ID";
    private final String COURSE_KEY = "COURSE_NAME";
    // firebaseDB variables
    private DatabaseReference mDatabase;
    private String mUserId;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private String courseName;
    private String entryID;
    private BookListing entry = new BookListing();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_listing_activity);
//        BookListingDataSource dataSource = new BookListingDataSource(this);
//        dataSource.open();
        Long rowId = getIntent().getLongExtra(ENTRY_KEY, 0);
        Log.d("DisplayEntry: ", "" + rowId);
        entryID = getIntent().getStringExtra(ENTRY_KEY);
        courseName = getIntent().getStringExtra(COURSE_KEY);
        Log.d("DisplayTAG", entryID);
        Log.d("DisplayTAG", courseName);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("courses").child(courseName).
                child("listings").child(entryID).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        entry = dataSnapshot.getValue(BookListing.class);
                        if(entry != null) {
                            TextView bookTitleTextView = (TextView) findViewById(R.id.bookTitleTextView);
                            bookTitleTextView.setText(entry.getBookTitle());
                            TextView priceTextView = (TextView) findViewById(R.id.priceTextView);
                            priceTextView.setText("" + entry.getPrice());
                            TextView courseNameTextView = (TextView) findViewById(R.id.courseNameTextView);
                            courseNameTextView.setText(entry.getClassName());
                            TextView accountNameTextView = (TextView) findViewById(R.id.accountNameTextView);
                            accountNameTextView.setText(entry.getPosterUsername());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
//        TextView bookTitleTextView = (TextView) findViewById(R.id.bookTitleTextView);
//        bookTitleTextView.setText(entry.getBookTitle());
//        TextView priceTextView = (TextView) findViewById(R.id.priceTextView);
//        priceTextView.setText(""+ entry.getPrice());
//        TextView courseNameTextView = (TextView) findViewById(R.id.courseNameTextView);
//        courseNameTextView.setText(entry.getClassName());
//        TextView accountNameTextView = (TextView) findViewById(R.id.accountNameTextView);
//        accountNameTextView.setText(entry.getPosterUsername());

        // Initialize mAuth, mUser, and mDatabase
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        // get keys and course names so we can find the listing in the database
//        listingKey = entry.getId();

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
        //TODO add the intent to open gmail with the user's email
        finish();
    }
    public void onCloseClicked(View view){
        finish();
    }
    public void onDeleteClicked(View view){
        // remove the listing from the database under both the course and the user
        mDatabase.child("courses").child(courseName).child("listings").child(entryID).removeValue();
        mDatabase.child("users").child(mUserId).child("listings").child(entryID).removeValue();
        finish();
    }
}
