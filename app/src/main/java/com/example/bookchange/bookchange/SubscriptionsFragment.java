package com.example.bookchange.bookchange;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Ted on 2/26/2017.
 */

public class SubscriptionsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}
