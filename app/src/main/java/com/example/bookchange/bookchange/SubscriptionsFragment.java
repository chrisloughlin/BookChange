package com.example.bookchange.bookchange;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceFragment;
import android.util.Log;

/**
 * Created by Ted on 2/26/2017.
 */

public class SubscriptionsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key){
       /* MainActivity mainActivity = (MainActivity)getActivity();
        boolean checked = sharedPreferences.getBoolean(key, false);
        String subToAdd = sharedPreferences.getString(key,"-1");
        if(!subToAdd.equals("-1")) {
            mainActivity.addSubToAccount(subToAdd);
        }*/
    }

    @Override
    public void onPause(){
        super.onPause();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
