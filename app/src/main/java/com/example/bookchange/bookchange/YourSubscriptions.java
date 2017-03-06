package com.example.bookchange.bookchange;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by sachinvadodaria on 3/2/17.
 */

public class YourSubscriptions extends Fragment {

    private final String ENTRY_KEY = "ENTRY_ID";
    // firebaseDB variables
    private DatabaseReference mDatabase;
    private String userId;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private ArrayList<Subscription> subscriptions;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // Initialize mAuth, mUser, and mDatabase
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        // make sure the user is logged in
        if (mUser == null) {
            // Not logged in, launch LoginActivity
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            this.startActivity(intent);
        } else {
            userId = mUser.getUid(); // get the Uid
        }
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup paramsGroup, Bundle savedInstanceState){

        View mView = inflater.inflate(R.layout.yoursubsfragment, paramsGroup, false);
        // get the selected course
//        MainActivity activity = (MainActivity) getActivity();
//        BookchangeAccount account = activity.getAccount();

        subscriptions = new ArrayList<>();

        // set up the listView
        ListView listView = (ListView) mView.findViewById(R.id.yoursubslist);
//        BookListingDataSource dataSource = new BookListingDataSource(getActivity());
//        dataSource.open();
//        child("users").child(userId).
        subscriptions.clear();
        mDatabase.child("users").child(userId).child("subscriptions").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                subscriptions.add(dataSnapshot.getValue(Subscription.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                subscriptions.remove(dataSnapshot.getValue(Subscription.class));
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        final ArrayList<BookListing> listings = dataSource.fetchEntriesByPosterUsername(account.getAccountName());
//        dataSource.close();
        SubscriptionsAdapter adapter = new SubscriptionsAdapter(getContext(), subscriptions);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getActivity().startActivity(new Intent(getActivity(), CourseView.class));
            }
        });

        return mView;
    }

    //TODO make the subscription update as soon as one is created

}
