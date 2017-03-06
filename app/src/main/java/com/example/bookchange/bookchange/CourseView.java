package com.example.bookchange.bookchange;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by christopher on 2/27/17.
 */

/*
This allows the user to display the listings that are currently posted for the course
 */

public class CourseView extends Fragment {
    private String courseName;
    private final String COURSE_KEY = "course_name";
    private final String ENTRY_KEY = "ENTRY_ID";
    Button subBtn;
    private DatabaseReference mDatabase;
    private String userId;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

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
        mDatabase = FirebaseDatabase.getInstance().getReference(userId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup paramsGroup, Bundle savedInstanceState){

        View mView = inflater.inflate(R.layout.course_view_fragment, paramsGroup, false);
        subBtn = (Button) mView.findViewById(R.id.subBtn);
        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("subscriptions").push();
                mDatabase.child("subscriptions").setValue(courseName);
            }
        });

        // get the selected course
        courseName = getArguments().getString(COURSE_KEY);
        // Set the text for the title
        TextView courseTitle = (TextView) mView.findViewById(R.id.title_course);
        courseTitle.setText(courseName);

        // set up the listView
        ListView listView = (ListView) mView.findViewById(R.id.course_listings);
        BookListingDataSource dataSource = new BookListingDataSource(getActivity());
        dataSource.open();
        final ArrayList<BookListing> listings = dataSource.fetchEntriesByClass(courseName);
        dataSource.close();
        BookListingAdapter adapter = new BookListingAdapter(getActivity(), listings);
        listView.setAdapter(adapter);
        if(listings.size()>0) {
            TextView noListings = (TextView) mView.findViewById(R.id.no_listings_text);
            noListings.setVisibility(View.GONE);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                intent = new Intent(getActivity(), DisplayListingActivity.class);
//                long entryId = listings.get(position).getId();
//                intent.putExtra(ENTRY_KEY, entryId);
                getActivity().startActivity(intent);
            }
        });

        return mView;
    }
}
