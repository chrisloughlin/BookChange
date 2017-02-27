package com.example.bookchange.bookchange;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by christopher on 2/26/17.
 */

public class HomeFragment extends Fragment{

    private BookListingDataSource dataSource;
    private ArrayList<BookListing> listings;
    private ArrayList<String> subscriptions;
    private ListView listView;
    private BookListingAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState){
        super.onCreateView(layoutInflater,viewGroup,savedInstanceState);
        View view = layoutInflater.inflate(R.layout.home_fragment,viewGroup,false);
        dataSource = new BookListingDataSource(getActivity());
        dataSource.open();
        MainActivity activity = (MainActivity) getActivity();
        BookchangeAccount account = activity.getAccount();
        subscriptions = account.getSubscriptions();
        listings = dataSource.fetchSubscriptions(subscriptions);
        dataSource.close();
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
