package com.example.bookchange.bookchange;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RatingBar;

/**
 * Created by christopher on 3/8/17.
 */

public class RateTransactionActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_transaction_id);
    }

    public void onSubmitRatingClicked(View view){
        RatingBar stars = (RatingBar) findViewById(R.id.stars);
        float rating = stars.getRating();
        //TODO: Something with the rating
        finish();
    }
}
