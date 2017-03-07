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
import android.widget.ProgressBar;
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

    private DatabaseReference mDatabase;
    private String userId;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private Boolean waited = false;
    private ArrayList<BookListing> listings = new ArrayList<>();
    BookListingAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState){
        super.onCreateView(layoutInflater,viewGroup,savedInstanceState);
        final View view = layoutInflater.inflate(R.layout.home_fragment,viewGroup,false);

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

        adapter = new BookListingAdapter(getActivity(), listings);

        mDatabase.child("users").child(userId).child("subscriptions").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                listings.clear();
                mDatabase.child("courses").child(dataSnapshot.getValue(String.class)).child("listings")
                        .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Log.d("HomeTAG", "added a listing to the arrayList");
                        BookListing newListing = dataSnapshot.getValue(BookListing.class);
                        listings.add(newListing);
                        Log.d("HomeTAG", "Listings size: "+ listings.size());
                        }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        listings.remove(dataSnapshot.getValue(BookListing.class));
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("Listener", " Cancelled");
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

        Log.d("HomeTAG", "Listings size: "+ listings.size());
        if(!waited) {
            new LoadDataWait().execute();
        }
        else if(listings.size()>0){
            ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.GONE);
            listView = (ListView) view.findViewById(R.id.home_list_view);
            listView.setAdapter(adapter);
            listView.setVisibility(View.VISIBLE);

        }
        else{
            ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.GONE);
            Button subButton = (Button) view.findViewById(R.id.add_subs);
            TextView textView = (TextView) view.findViewById(R.id.home_text_1);
            TextView textView2 = (TextView) view.findViewById(R.id.home_text_2);
            subButton.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            textView2.setVisibility(View.VISIBLE);

        }

        return view;
    }

    public class LoadDataWait extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void...voids) {
            try{
                Thread.sleep(3000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            waited= true;
            View view = getView();
            ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.GONE);
            if(listings.size()>0) {
                Log.d("HomeTAG", "go inside the if statement, listings was populated" + listings.size());
                listView = (ListView) view.findViewById(R.id.home_list_view);
                listView.setAdapter(adapter);
                listView.setVisibility(View.VISIBLE);
            }
            else {
                Button subButton = (Button) view.findViewById(R.id.add_subs);
                TextView textView = (TextView) view.findViewById(R.id.home_text_1);
                TextView textView2 = (TextView) view.findViewById(R.id.home_text_2);
                subButton.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.VISIBLE);
            }


        }
    }
}

