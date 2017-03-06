package com.example.bookchange.bookchange;

import android.content.Context;
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

    public SubscriptionsAdapter(Context context, List<Subscription> values){
//        android.R.layout.simple_list_item_1
        super(context, R.layout.subscriptionrow, R.id.subbed_course, values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parentViewGroup){
        View view = super.getView(position, convertView, parentViewGroup);
        TextView textView = (TextView) view.findViewById(R.id.subbed_course);
        Subscription entry = (Subscription) getItem(position);

//        ArrayList classes = entry.getClasses();

        textView.setText(entry.getClasses());

        return view;
    }

//    private final ArrayList</*ExerciseEntry*/> entries;
//    private final ArrayList<String> subs;
//
//    public SubscriptionsAdapter(Context context, ArrayList<ExerciseEntry> data) {
//        super(context, android.R.layout.simple_list_item_2, android.R.id.text1, data);
//        subs = data;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View view = super.getView(position, convertView, parent);
//        TextView tV1 = (TextView) view.findViewById(android.R.id.text1);
//        TextView tV2 = (TextView) view.findViewById(android.R.id.text2);
//
//        String line1 = subs.get(position).getInputType() + ": " + entries.get(position).getActivityType() + ", "
//                + HistoryFragment.convertDate(subs.get(position).getDateAndTime().getTimeInMillis(), "hh:mm:ss MMM dd yyyy");
//        String line2 = entries.get(position).getDistance() + " Miles, " + subs.get(position).getDuration() + "secs";
//
//        tV1.setText(line1);
//        tV2.setText(line2);
//        return view;
//    }

}
