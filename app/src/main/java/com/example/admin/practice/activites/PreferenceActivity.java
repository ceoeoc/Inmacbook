package com.example.admin.practice.activites;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.admin.practice.R;

public class PreferenceActivity extends android.preference.PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        getFragmentManager().beginTransaction().replace(android.R.id.content,new PreferenceFragment()).commit();

    }

    public static class PreferenceFragment extends android.preference.PreferenceFragment{
        @Override
        public void onCreate(Bundle savedInstance){
            super.onCreate(savedInstance);
            addPreferencesFromResource(R.xml.settings_preference);
        }
    }
}
