package com.example.bookchange.bookchange;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by christopher on 2/26/17.
 */

public class HomeFragment extends Fragment{

    private BookListingDataSource dataSource;
    private ListView listView;
    private BookListingAdapter adapter;

    private DatabaseReference mDatabase;
    private String userId;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private ArrayList<BookListing> listings = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState){
        super.onCreateView(layoutInflater,viewGroup,savedInstanceState);
        View view = layoutInflater.inflate(R.layout.home_fragment,viewGroup,false);

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
            userId = mUser.getUid(); // get the Uid
        }
//        dataSource = new BookListingDataSource(getActivity());
//        dataSource.open();
//        MainActivity activity = (MainActivity) getActivity();
//        BookchangeAccount account = activity.getAccount();
//        subscriptions = account.getSubscriptions();
//        listings = dataSource.fetchSubscriptions(subscriptions);
//        dataSource.close();
        listings.clear();
        mDatabase.child("users").child(userId).child("subscriptions")
                .addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mDatabase.child("courses").child(dataSnapshot.getValue(Subscription.class)
                        .getClasses()).child("listings")
                        .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Log.d("ListingsTAG", "added a listing to the arrayList");
                        BookListing newListing = dataSnapshot.getValue(BookListing.class);
                        listings.add(newListing);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        listings.remove(dataSnapshot.getValue(BookListing.class));
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });


        if(listings.size()>0) {
            Button subButton = (Button) view.findViewById(R.id.add_subs);
            TextView textView = (TextView) view.findViewById(R.id.home_text_1);
            TextView textView2 = (TextView) view.findViewById(R.id.home_text_2);
            subButton.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            textView2.setVisibility(View.GONE);
            listView = (ListView) view.findViewById(R.id.home_list_view);
            adapter = new BookListingAdapter(getActivity(), listings);
            listView.setAdapter(adapter);
        }
        else {

        }
        return view;
    }


}
