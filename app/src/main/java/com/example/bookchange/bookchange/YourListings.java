package com.example.bookchange.bookchange;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by christopher on 2/27/17.
 */

public class YourListings extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup paramsGroup, Bundle savedInstanceState){

        View mView = inflater.inflate(R.layout.your_postings_fragment, paramsGroup, false);
        // get the selected course
        MainActivity activity = (MainActivity) getActivity();
        BookchangeAccount account = activity.getAccount();

        // set up the listView
        ListView listView = (ListView) mView.findViewById(R.id.your_postings_list);
        BookListingDataSource dataSource = new BookListingDataSource(getActivity());
        dataSource.open();
        ArrayList<BookListing> listings = dataSource.fetchEntriesByPosterUsername(account.getAccountName());
        dataSource.close();
        BookListingAdapter adapter = new BookListingAdapter(getActivity(), listings);
        listView.setAdapter(adapter);

        return mView;
    }
}
