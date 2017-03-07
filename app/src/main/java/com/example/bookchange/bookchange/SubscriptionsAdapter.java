package com.example.bookchange.bookchange;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sachinvadodaria on 3/3/17.
 */

public class SubscriptionsAdapter extends ArrayAdapter {

    public SubscriptionsAdapter(Context context, List<String> values){
//        android.R.layout.simple_list_item_1
        super(context, R.layout.subscriptionrow, R.id.subbed_course, values);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parentViewGroup){
        View view = super.getView(position, convertView, parentViewGroup);
        TextView textView = (TextView) view.findViewById(R.id.subbed_course);
        textView.setText((String) getItem(position));
        return view;
    }

}
