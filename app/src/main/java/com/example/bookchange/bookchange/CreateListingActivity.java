package com.example.bookchange.bookchange;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by christopher on 2/27/17.
 */

public class CreateListingActivity extends AppCompatActivity {
    private String accountName;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_listing_activity);
        accountName = getIntent().getStringExtra("account_name");
        ((TextView)findViewById(R.id.accountNameText)).setText(accountName);
    }

    public void onSaveListingClicked(View view){
        String bookTitle = ((EditText)findViewById(R.id.bookTitleEditText)).getText().toString();
        String price = ((EditText)findViewById(R.id.priceEditText)).getText().toString();
        String courseName = ((EditText)findViewById(R.id.courseNameEditText)).getText().toString();
        if (bookTitle.equals("")){
            Toast.makeText(this,"Add Book Title",Toast.LENGTH_SHORT);
        }
        else  if (price.equals("")){
            Toast.makeText(this,"Add Price",Toast.LENGTH_SHORT);
        }
        else if (courseName.equals("")){
            Toast.makeText(this,"Add Course Name",Toast.LENGTH_SHORT);
        }
        else {
            BookListing bookListing = new BookListing(accountName, Double.parseDouble(price), bookTitle, courseName);
            BookListingDataSource dataSource = new BookListingDataSource(this);
            dataSource.open();
            dataSource.insertEntry(bookListing);
            dataSource.close();
            finish();
        }

    }

    public void onCancelListingClicked(View view){
        finish();
    }
}
