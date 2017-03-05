package com.example.bookchange.bookchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by christopher on 2/27/17.
 */

public class CreateListingActivity extends AppCompatActivity {
    private String userDisplayName;
    // firebaseDB variables
    private DatabaseReference mDatabase;
    private String mUserId;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_listing_activity);
        userDisplayName = getIntent().getStringExtra("account_name");
        ((TextView)findViewById(R.id.accountNameText)).setText(userDisplayName);

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
            mUserId = mUser.getUid(); // get the Uid
        }
    }

    public void onSaveListingClicked(View view){
        String bookTitle = ((EditText)findViewById(R.id.bookTitleEditText)).getText().toString();
        String price = ((EditText)findViewById(R.id.priceEditText)).getText().toString();
        String courseName = ((EditText)findViewById(R.id.courseNameEditText)).getText().toString();
        if (bookTitle.equals("")){
            Toast toast = Toast.makeText(this,"Add Book Title",Toast.LENGTH_SHORT);
            toast.show();
            ((EditText)findViewById(R.id.bookTitleEditText)).setError("Required");
        }
        else  if (price.equals("")){
            Toast toast = Toast.makeText(this,"Add Price",Toast.LENGTH_SHORT);
            toast.show();
            ((EditText)findViewById(R.id.priceEditText)).setError("Required");
        }
        else if (courseName.equals("")){
           Toast toast = Toast.makeText(this,"Add Course Name",Toast.LENGTH_SHORT);
            toast.show();
            ((EditText)findViewById(R.id.courseNameEditText)).setError("Required");
        }
        else {
            BookListing bookListing = new BookListing(userDisplayName, Double.parseDouble(price), bookTitle, courseName);
            BookListingDataSource dataSource = new BookListingDataSource(this);
            dataSource.open();
            dataSource.insertEntry(bookListing);
            dataSource.close();

            // insert a new listing in the firebaseDB under courses/coursename/listings
            DatabaseReference pushedListingsRef = mDatabase.child("courses").child(courseName).
                    child("listings").push();
            bookListing.setId(pushedListingsRef.getKey());
            pushedListingsRef.setValue(bookListing);
            // insert a listing under users/mUserID/listings
            mDatabase.child("users").child(mUserId).child("listings").push().setValue(bookListing);
            finish();
        }

    }

    public void onCancelListingClicked(View view){
        finish();
    }
}
