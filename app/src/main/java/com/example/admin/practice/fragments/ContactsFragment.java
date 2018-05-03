package com.example.admin.practice.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.admin.practice.activites.MainActivity;
import com.example.admin.practice.adapters.ContactsAdapter;
import com.example.admin.practice.R;
import com.wafflecopter.multicontactpicker.ContactResult;
import com.wafflecopter.multicontactpicker.MultiContactPicker;

import java.util.List;
import java.util.Random;


public class ContactsFragment extends Fragment {
    private static final int CONTACT_PICKER_REQUEST = 991;

    FloatingActionButton fab;

    private ListView mListView;
    private ContactsAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contacts_fragment, container, false);
        mListView = (ListView) rootView.findViewById(R.id.listview);
        //dataSetting();
        return rootView;
    }

/*
    private void dataSetting(){
        mAdapter = new ContactsAdapter();
        Random r = new Random();
        String[] arrProjection = {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };

        Cursor clsCursor = getActivity().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                arrProjection,
                ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1",
                null, null);

        while(clsCursor.moveToNext()){



            mAdapter.addItem(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.icon), clsCursor.getString(0),clsCursor.getString(1),r.nextInt(100));
        }
        clsCursor.close();
        mAdapter.notifyDataSetChanged();
        mListView.setAdapter(mAdapter);
    }*/
}
