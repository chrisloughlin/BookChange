package com.example.bookchange.bookchange;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by christopher on 2/27/17.
 */

public class DisplayListingActivity extends AppCompatActivity {
    private final String ENTRY_KEY = "ENTRY_ID";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_listing_activity);
        BookListingDataSource dataSource = new BookListingDataSource(this);
        dataSource.open();
        Long rowId = getIntent().getLongExtra(ENTRY_KEY, 0);
        Log.d("DisplayEntry: ", "" + rowId);
        BookListing entry = dataSource.fetchEntryByIndex(rowId);
        TextView bookTitleTextView = (TextView) findViewById(R.id.bookTitleTextView);
        bookTitleTextView.setText(entry.getBookTitle());
        TextView priceTextView = (TextView) findViewById(R.id.priceTextView);
        priceTextView.setText(""+ entry.getPrice());
        TextView courseNameTextView = (TextView) findViewById(R.id.courseNameTextView);
        courseNameTextView.setText(entry.getClassName());
        TextView accountNameTextView = (TextView) findViewById(R.id.accountNameTextView);
        accountNameTextView.setText(entry.getPosterUsername());
    }

    public void onContactClicked(View view){
        finish();
    }
    public void onCloseClicked(View view){
        finish();
    }
    public void onDeleteClicked(View view){
        finish();
    }
}
