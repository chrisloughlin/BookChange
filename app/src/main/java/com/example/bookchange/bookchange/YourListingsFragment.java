package com.example.bookchange.bookchange;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by christopher on 2/27/17.
 */

public class YourListingsFragment extends Fragment {
    private final String ENTRY_KEY = "ENTRY_ID";
    private final String COURSE_KEY = "COURSE_NAME";

    // firebaseDB variables
    private DatabaseReference mDatabase;
    private String mUserId;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private ArrayList<BookListing> listings;
    private ChildEventListener listener;
    public boolean StoppedByActivity = false;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // Initialize mAuth, mUser, and mDatabase
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // make sure the user is logged in
        if (mUser == null) {
            // Not logged in, launch LoginActivity
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            this.startActivity(intent);
        } else {
            mUserId = mUser.getUid(); // get the Uid
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup paramsGroup, Bundle savedInstanceState){

        View mView = inflater.inflate(R.layout.your_postings_fragment, paramsGroup, false);
        // get the selected course
        MainActivity activity = (MainActivity) getActivity();
        //BookchangeAccount account = activity.getAccount();

        // set up the listView
        ListView listView = (ListView) mView.findViewById(R.id.your_postings_list);
        listings = new ArrayList<>();
//        listings.clear();
        final BookListingAdapter adapter = new BookListingAdapter(getActivity(), listings);
        listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("ListingsTAG", "added a listing to the arrayList, yourListings");
                BookListing newListing = dataSnapshot.getValue(BookListing.class);
                listings.add(newListing);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("DeleteTAG", "Got here");
                Log.d("DeleteTAG", "Size of listings before remove: " + listings.size());
                BookListing newListing = dataSnapshot.getValue(BookListing.class);
                listings.remove(newListing);
                Log.d("DeleteTAG", "Size of listings after remove: " + listings.size());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        mDatabase.child("users").child(mUserId).child("listings").addChildEventListener(listener);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                intent = new Intent(getActivity(), DisplayListingActivity.class);
                String entryId = listings.get(position).getId();
                String courseName = listings.get(position).getClassName();
                intent.putExtra(ENTRY_KEY, entryId);
                intent.putExtra(COURSE_KEY, courseName);
                StoppedByActivity = true;
                getActivity().startActivity(intent);
            }
        });

        return mView;
    }

    @Override
    public void onStart(){
        super.onStart();
        StoppedByActivity = false;
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d("onStopTAG", "onStop was called");
        if (!StoppedByActivity) {
            mDatabase.child("users").child(mUserId).child("listings").removeEventListener(listener);
        }
    }
}
