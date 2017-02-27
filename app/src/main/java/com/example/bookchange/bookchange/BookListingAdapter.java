package com.example.bookchange.bookchange;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by christopher on 2/27/17.
 */

public class BookListingAdapter extends ArrayAdapter {
    private static int pref;
    private final String UNIT_KEY = "unit_setting";

    public BookListingAdapter(Context context, List<BookListing> values){
        super(context, android.R.layout.simple_list_item_2, android.R.id.text1, values);
        pref = 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parentViewGroup){
        View view = super.getView(position, convertView, parentViewGroup);
        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        TextView textView2 = (TextView) view.findViewById(android.R.id.text2);
        BookListing entry = (BookListing) getItem(position);

        String bookTitle = entry.getBookTitle();
        String price = "$"+entry.getPrice();
        String poster = entry.getPosterUsername();
        String className = entry.getClassName();

        String topLine = className +": " + bookTitle;
        String bottomLine = price + ", by " + poster;
        textView.setText(topLine);
        textView2.setText(bottomLine);

        return view;
    }
}
