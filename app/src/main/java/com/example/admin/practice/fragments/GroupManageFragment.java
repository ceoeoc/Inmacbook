package com.example.admin.practice.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.admin.practice.DB.CIDBHandler;
import com.example.admin.practice.R;
import com.example.admin.practice.activites.MainActivity;
import com.example.admin.practice.adapters.ContactsAdapter;

import java.lang.String;
import java.util.ArrayList;

public class GroupManageFragment extends DialogFragment {
    public static final String TAG = "GroupManageFragment";
    private static final String Key = "GroupManage";
    private ImageButton oB,cB;
    private Button nG;
    private ListView mListView;
    private ContactsAdapter mAdapter;

    private ArrayList<String> groups;
    private CIDBHandler Cdh;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.group_fragment, container, false);
        Cdh = new CIDBHandler(getActivity());
        Cdh.open();
        mAdapter = new ContactsAdapter();
        mListView = (ListView) rootView.findViewById(R.id.grouplist);

        mListView.setAdapter(mAdapter);

        for(int i = 0 ; i < MainActivity.groups.size();i++){
            mAdapter.addItem(1,Cdh.sizeofData(MainActivity.groups.get(i),0),MainActivity.groups.get(i),null);
        }

        cB = (ImageButton) rootView.findViewById(R.id.cancel);
        oB = (ImageButton) rootView.findViewById(R.id.confrim);
        oB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        cB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        nG = (Button) rootView.findViewById(R.id.bt_newgroup);
        nG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return rootView;
    }
}
