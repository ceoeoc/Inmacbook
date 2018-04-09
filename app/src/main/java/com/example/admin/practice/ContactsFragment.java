package com.example.admin.practice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Random;


public class ContactsFragment extends Fragment {

    private ListView mListView;
    private ContactsAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contacts_fragment, container, false);

        mListView = (ListView) rootView.findViewById(R.id.listview);
        mAdapter = new ContactsAdapter();
        Random r = new Random();
        for(int i = 0 ; i < 10 ; i++){
            mAdapter.addItem(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.icon), "name_" + i, "contents_" + i,r.nextInt(100));
        }
        mAdapter.notifyDataSetChanged();
        mListView.setAdapter(mAdapter);
        return rootView;
    }
}
