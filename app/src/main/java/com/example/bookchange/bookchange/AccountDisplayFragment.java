package com.example.bookchange.bookchange;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by christopher on 3/7/17.
 */

public class AccountDisplayFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, viewGroup, savedInstanceState);
        View view = layoutInflater.inflate(R.layout.account_display,viewGroup,false);
        TextView accountName = (TextView) view.findViewById(R.id.account_page_account_name);
        accountName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        return view;
    }

}