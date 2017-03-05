package com.example.bookchange.bookchange;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by Ted on 2/26/2017.
 */

public class SubscriptionsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
   private MainActivity mainActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity)getActivity();
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
        SharedPreferences preferences = getPreferenceManager().getSharedPreferences();
        preferences.registerOnSharedPreferenceChangeListener(this);
        updateSubsOnStart(preferences);
    }

    public void updateSubsOnStart(SharedPreferences preferences){
        Iterator<String> keys = preferences.getAll().keySet().iterator();
        while(keys.hasNext()){
            String key = keys.next();
            CheckBoxPreference checkBoxPreference = (CheckBoxPreference) findPreference(key);
            boolean checked = checkBoxPreference.isChecked();
            if(checked){
                String course = checkBoxPreference.getTitle().toString();
                Log.d(course, "Added to subs");
                mainActivity.addSubToAccount(course);
            }
        }
    }

    public void onSharedPreferenceChanged(SharedPreferences preferences, String key){
        Log.d("Shared Pref", " change noted");
        CheckBoxPreference  checkBoxPreference = (CheckBoxPreference) findPreference(key);
        boolean checked = checkBoxPreference.isChecked();
        if(checked){
            String course = checkBoxPreference.getTitle().toString();
            Log.d(course, "Added to subs");
            mainActivity.addSubToAccount(course);
        }
        else{
            String course = checkBoxPreference.getTitle().toString();
            Log.d(course, "Removed from subs");
            mainActivity.removeSubFromAccount(course);
        }
    }

    /*@Override
    public void onPause(){
        super.onPause();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }*/
}
