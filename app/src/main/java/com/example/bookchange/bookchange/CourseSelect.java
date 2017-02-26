package com.example.bookchange.bookchange;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Ted on 2/26/2017.
 */

public class CourseSelect extends AppCompatActivity {
    private int departmentInt;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_select);

        // get the int for the selected department
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
               departmentInt = -1;
            } else {
                departmentInt = extras.getInt("department_int");
            }
        } else {
            departmentInt = savedInstanceState.getInt("department_key");
        }

        // Set the text for the title
        TextView departmentTitle = (TextView) findViewById(R.id.title_department);
        Resources res = getResources();
        String[] departments = res.getStringArray(R.array.departments_array);
        departmentTitle.setText(departments[departmentInt]);
    }
}
