package com.example.bookchange.bookchange;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by christopher on 2/26/17.
 */

public class HomeFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState){
        super.onCreateView(layoutInflater,viewGroup,savedInstanceState);
        return layoutInflater.inflate(R.layout.home_fragment,viewGroup,false);
    }

}
