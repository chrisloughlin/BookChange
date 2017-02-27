package com.example.bookchange.bookchange;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ted on 2/26/2017.
 */

public class CourseSelect extends Fragment {
    private int departmentInt;
    private final String COURSE_KEY = "course_name";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup paramsGroup, Bundle savedInstanceState){

        View mView = inflater.inflate(R.layout.course_select, paramsGroup, false);
        // get the int for the selected department
        departmentInt = getArguments().getInt("department_int");
        // Set the text for the title
        TextView departmentTitle = (TextView) mView.findViewById(R.id.title_department);
        Resources res = getResources();
        String[] departments = res.getStringArray(R.array.departments_array);
        departmentTitle.setText(departments[departmentInt]);

        // set up the listView
        ListView coursesListView = (ListView) mView.findViewById(R.id.list);
        final String[] courses = { "CS 1", "CS 10", "CS 30", "CS 50", "CS 65" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, courses);
        coursesListView.setAdapter(adapter);
        coursesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // start a fragment with info about the clicked course
                CourseView fragment = new CourseView();
                Bundle bundle = new Bundle();
                String courseName = courses[position];
                bundle.putString(COURSE_KEY,courseName);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag, fragment);
                fragmentTransaction.commit();
            }
        });

        return mView;
    }
}
