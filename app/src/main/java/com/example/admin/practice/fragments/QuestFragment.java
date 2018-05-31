package com.example.admin.practice.fragments;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.admin.practice.ContactsItem;
import com.example.admin.practice.DB.CIDBHandler;
import com.example.admin.practice.DB.EVDBHandler;
import com.example.admin.practice.EventItem;
import com.example.admin.practice.ListViewItem;
import com.example.admin.practice.R;
import com.example.admin.practice.adapters.ContactsAdapter;
import com.example.admin.practice.adapters.EventAdapter;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class QuestFragment extends Fragment{
    private EventAdapter mAdapter;
    private ListView mListView;
    private List<EventItem> lists;
    private EVDBHandler dh ;
    private FloatingActionButton eafab,emfab;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.event_fragment, container, false);

        eafab = (FloatingActionButton) rootView.findViewById(R.id.eafab);
        eafab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventMakeDialogFragment Edialog = new EventMakeDialogFragment();
                Edialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme );
                Edialog.show(getFragmentManager(),"EventMakeDialogFragment");
            }
        });
        emfab = (FloatingActionButton) rootView.findViewById(R.id.emfab);

        mListView = (ListView) rootView.findViewById(R.id.elistview);
        dh = new EVDBHandler(getActivity());
        dh.open();
        mAdapter = new EventAdapter();
        lists = dh.getData();

        mAdapter.addItem(lists);
        mListView.setAdapter(mAdapter);

        return rootView;
    }
}
