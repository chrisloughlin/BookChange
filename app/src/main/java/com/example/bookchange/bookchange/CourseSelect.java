package com.example.bookchange.bookchange;

import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ted on 2/26/2017.
 */

public class CourseSelect extends Fragment {
    private int departmentInt;

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
        String[] courses = { "CS 1", "CS 10", "CS 30", "CS 50", "CS 65" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, courses);
        coursesListView.setAdapter(adapter);

        return mView;
    }
}
