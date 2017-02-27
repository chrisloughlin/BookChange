package com.example.bookchange.bookchange;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by christopher on 2/27/17.
 */

public class YourListings extends Fragment {
    private final String ENTRY_KEY = "ENTRY_ID";

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
        final ArrayList<BookListing> listings = dataSource.fetchEntriesByPosterUsername(account.getAccountName());
        dataSource.close();
        BookListingAdapter adapter = new BookListingAdapter(getActivity(), listings);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                intent = new Intent(getActivity(), DisplayListingActivity.class);
                long entryId = listings.get(position).getId();
                intent.putExtra(ENTRY_KEY, entryId);
                getActivity().startActivity(intent);
            }
        });

        return mView;
    }
}
