package com.example.admin.practice;

import android.os.Bundle;
import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingPreferenceFragment extends PreferenceFragment {

    SharedPreferences prefs;

    PreferenceScreen keywordScreen;

    @Override
    public void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);

        addPreferencesFromResource(R.xml.settings);
    }
}
