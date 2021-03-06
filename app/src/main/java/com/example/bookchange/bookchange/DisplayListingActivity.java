package com.example.bookchange.bookchange;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

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

        Long rowId = getIntent().getLongExtra(ENTRY_KEY, 0);
        entryID = getIntent().getStringExtra(ENTRY_KEY);
        courseName = getIntent().getStringExtra(COURSE_KEY);

        // Initialize mAuth, mUser, and mDatabase
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        String email;

        // make sure the user is logged in
        if (mUser == null) {
            // Not logged in, launch LoginActivity
            Intent intent = new Intent(this,LoginActivity.class);
            this.startActivity(intent);
            finish();
        } else {
            mUserId = mUser.getUid(); // get the Uid
        }
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

                            final ImageView bookPic = (ImageView) findViewById(R.id.bookPic);
                            final TextView info1 = (TextView) findViewById(R.id.info1);
                            if(!entry.getBookPic().isEmpty()){
                                final Bitmap decodedBitmap = decodeFromBase64(entry.getBookPic());
                                bookPic.setVisibility(View.VISIBLE);
                                info1.setVisibility(View.VISIBLE);
                                bookPic.setImageBitmap(decodedBitmap);
                                final ImageView expandedImage = (ImageView) findViewById(R.id.expandedImage);
                                final TextView info = (TextView) findViewById(R.id.info);
                                bookPic.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        bookPic.setVisibility(View.GONE);
                                        info.setVisibility(View.VISIBLE);
                                        expandedImage.setVisibility(View.VISIBLE);
                                        expandedImage.setImageBitmap(decodedBitmap);

                                        expandedImage.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                expandedImage.setVisibility(View.GONE);
                                                info.setVisibility(View.GONE);
                                                bookPic.setVisibility(View.VISIBLE);
                                                bookPic.setImageBitmap(decodedBitmap);
                                            }
                                        });
                                    }
                                });
                            }
                            else{
                                bookPic.getLayoutParams().width = 250;
                                bookPic.getLayoutParams().height = 250;
                                bookPic.setImageResource(R.drawable.noimage);
                            }

                            if(!mUser.getDisplayName().equals(entry.getPosterUsername())){
                                Button delete = (Button) findViewById(R.id.delete_button);
                                delete.setVisibility(View.GONE);

                            }
                            else{
                                Button contact = (Button) findViewById(R.id.contact_button);
                                contact.setVisibility(View.GONE);
                            }
                            TextView emailTextView = (TextView) findViewById(R.id.emailTextView);
                            emailTextView.setText(entry.getEmail());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }

    public static Bitmap decodeFromBase64(String image){
        byte[] decodedByteArray = Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

    public void onContactClicked(View view){
        TextView emailTextView = (TextView) findViewById(R.id.emailTextView);
        String posterEmail = emailTextView.getText().toString();

//        mAuth.

        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{posterEmail});
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, entry.getBookTitle() +" Book Purchase");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "Hello "+entry.getPosterUsername() +", \n \n I'd like to talk to you about buying " +
                entry.getBookTitle()+" for " +entry.getClassName()+ ". Please respond to this email if you are interested in selling the book." +
                "\n \n Thanks, \n"+mUser.getDisplayName());

/* Send it off to the Activity-Chooser */
        startActivity(Intent.createChooser(intent, "Send mail..."));
        //TODO add the intent to open gmail with the user's email
        finish();
    }
    public void onCloseClicked(View view){
        finish();
    }

    public void onCompleteTransactionClicked(View view){

        mDatabase.child("courses").child(courseName).child("listings").child(entryID).removeValue();
        mDatabase.child("users").child(mUserId).child("listings").child(entryID).removeValue();
        finish();
        //TODO: COMPLETE TRANSACTION
    }
    public void onDeleteClicked(View view){
        // remove the listing from the database under both the course and the user
        mDatabase.child("courses").child(courseName).child("listings").child(entryID).removeValue();
        mDatabase.child("users").child(mUserId).child("listings").child(entryID).removeValue();
        finish();
    }
}
