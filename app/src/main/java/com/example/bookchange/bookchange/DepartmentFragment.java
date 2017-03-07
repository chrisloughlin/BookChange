package com.example.bookchange.bookchange;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by christopher on 3/7/17.
 */

public class DepartmentFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, viewGroup, savedInstanceState);
        View view = layoutInflater.inflate(R.layout.departments_frag,viewGroup,false);
        ListView listView = (ListView) view.findViewById(R.id.departments_list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                CourseSelect fragment = new CourseSelect();
                bundle.putInt("department_int", position);
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().add(fragment,"detail").addToBackStack(null)
                        .replace(R.id.frag, fragment).commit();
            }
        });
        return view;
    }


}