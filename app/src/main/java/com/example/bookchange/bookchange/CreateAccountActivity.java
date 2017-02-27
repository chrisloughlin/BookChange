package com.example.bookchange.bookchange;

import android.content.Intent;
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

    // go to the main activity when create is clicked--temporary for demo
    public void onCreateClicked (View button){
        Intent intent = new Intent(this,MainActivity.class);
        this.startActivity(intent);
        finish();
    }

    public void onCancelClicked (View button){
        Intent intent = new Intent(this,LoginActivity.class);
        this.startActivity(intent);
        finish();
    }
}
