package com.example.bookchange.bookchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by christopher on 2/26/17.
 */

public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
    }

    public void onLoginClicked (View button){
        boolean loginResult = checkLogin();
        if(loginResult){
            Intent intent = new Intent(this,MainActivity.class);
            this.startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(this,MainActivity.class);
            this.startActivity(intent);
            finish();
        }
    }

    public void onCreateAccountClicked (View button){
        Intent intent = new Intent(this,CreateAccountActivity.class);
        this.startActivity(intent);
        finish();
    }

    public boolean checkLogin(){
        return false;
    }
}
