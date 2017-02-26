package com.example.bookchange.bookchange;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by christopher on 2/26/17.
 */

public class CreateAccountActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
    }

    public void onCreateClicked (View button){
        finish();
    }

    public void onCancelClicked (View button){
        finish();
    }
}
