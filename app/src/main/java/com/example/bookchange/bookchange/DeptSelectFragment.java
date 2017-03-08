package com.example.bookchange.bookchange;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

/**
 * Created by Ted on 2/26/2017.
 */

/*
This fragment allows the user to select a course from the list of courses.
 */

public class DeptSelectFragment extends Fragment {
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
//        final String[] courses = { "CS 1", "CS 10", "CS 30", "CS 50", "CS 65" };
        // double array of arrays of courses
        final String[][] courseArrays = {res.getStringArray(R.array.AAASCourses),
                                        res.getStringArray(R.array.ANTHCourses),
                                        res.getStringArray(R.array.ARTHCourses),
                                        res.getStringArray(R.array.AMELCourses),
                                        res.getStringArray(R.array.AMESCourses),
                                        res.getStringArray(R.array.BIOLCourses),
                                        res.getStringArray(R.array.CHEMCourses),
                                        res.getStringArray(R.array.CLSTCourses),
                                        res.getStringArray(R.array.COGSCourses),
                                        res.getStringArray(R.array.COCOCourses),
                                        res.getStringArray(R.array.COLTCourses),
                                        res.getStringArray(R.array.COSCCourses),
                                        res.getStringArray(R.array.EARSCourses),
                                        res.getStringArray(R.array.ECONCourses),
                                        res.getStringArray(R.array.EDUCCourses),
                                        res.getStringArray(R.array.ENGSCourses),
                                        res.getStringArray(R.array.ENGLCourses),
                                        res.getStringArray(R.array.ENVSCourses),
                                        res.getStringArray(R.array.FILMCourses),
                                        res.getStringArray(R.array.FRENCourses),
                                        res.getStringArray(R.array.ITALCourses),
                                        res.getStringArray(R.array.GEOGCourses),
                                        res.getStringArray(R.array.GERMCourses),
                                        res.getStringArray(R.array.GOVTCourses),
                                        res.getStringArray(R.array.HISTCourses),
                                        res.getStringArray(R.array.HUMCourses),
                                        res.getStringArray(R.array.JWSTCourses),
                                        res.getStringArray(R.array.LACSCourses),
                                        res.getStringArray(R.array.LATSCourses),
                                        res.getStringArray(R.array.LINGCourses),
                                        res.getStringArray(R.array.MATHCourses),
                                        res.getStringArray(R.array.MUSCourses),
                                        res.getStringArray(R.array.NASCourses),
                                        res.getStringArray(R.array.PHILCourses),
                                        res.getStringArray(R.array.ASTRCourses),
                                        res.getStringArray(R.array.PHYSCourses),
                                        res.getStringArray(R.array.PSYCCourses),
                                        res.getStringArray(R.array.RELCourses),
                                        res.getStringArray(R.array.RUSSCourses),
                                        res.getStringArray(R.array.SSOCCourses),
                                        res.getStringArray(R.array.SOCYCourses),
                                        res.getStringArray(R.array.SPANCourses),
                                        res.getStringArray(R.array.PORTCourses),
                                        res.getStringArray(R.array.SARTCourses),
                                        res.getStringArray(R.array.THEACourses),
                                        res.getStringArray(R.array.WGSSCourses)};

        final String[] courses = courseArrays[departmentInt];
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, courses);
        coursesListView.setAdapter(adapter);
        coursesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // start a fragment with info about the clicked course
                CourseListFragment fragment = new CourseListFragment();
                Bundle bundle = new Bundle();
                String courseName = courses[position];
                bundle.putString(COURSE_KEY,courseName);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(fragment,"detail").addToBackStack(null)
                        .replace(R.id.frag, fragment).commit();
            }
        });

        return mView;
    }
}
