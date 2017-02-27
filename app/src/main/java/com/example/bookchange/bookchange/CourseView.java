package com.example.bookchange.bookchange;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by christopher on 2/27/17.
 */

public class CourseView extends Fragment {
    private String courseName;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup paramsGroup, Bundle savedInstanceState){

        View mView = inflater.inflate(R.layout.course_view_fragment, paramsGroup, false);
        // get the selected course
        courseName = getArguments().getString("course_name");
        // Set the text for the title
        TextView courseTitle = (TextView) mView.findViewById(R.id.title_course);
        courseTitle.setText(courseName);

        // set up the listView
        ListView listView = (ListView) mView.findViewById(R.id.course_listings);
        BookListingDataSource dataSource = new BookListingDataSource(getActivity());
        dataSource.open();
        ArrayList<BookListing> listings = dataSource.fetchEntriesByClass(courseName);
        dataSource.close();
        BookListingAdapter adapter = new BookListingAdapter(getActivity(), listings);
        listView.setAdapter(adapter);

        return mView;
    }
}
