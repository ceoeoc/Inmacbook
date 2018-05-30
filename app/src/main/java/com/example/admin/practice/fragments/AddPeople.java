package com.example.admin.practice.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.admin.practice.ContactsItem;
import com.example.admin.practice.DB.CIDBHandler;
import com.example.admin.practice.R;
import com.example.admin.practice.adapters.AddPeopleAdapter;

import java.util.List;

public class AddPeople extends DialogFragment {
    public static final String TAG = "AddPeopleFragment";
    private static final String Key = "AddPeople";

    private AddPeopleAdapter mAdapter;
    private ListView mListView;
    private List<ContactsItem> lists;
    private String args1;
    private CIDBHandler Cdh;

    public static AddPeople newInstance(String para){
        AddPeople f = new AddPeople();
        Bundle args = new Bundle();
        args.putString(Key,para);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            args1 = getArguments().getString(Key);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_people, container, false);
        Cdh = new CIDBHandler(getActivity());
        Cdh.open();
        mAdapter = new AddPeopleAdapter();
        mListView = (ListView) rootView.findViewById(R.id.listview);

        mListView.setAdapter(mAdapter);
        lists = Cdh.getGroupData("unknown");
        for(int i = 0; i < lists.size(); i++){
            mAdapter.addItem(lists.get(i));
        }

        Button addbtn = (Button) rootView.findViewById(R.id.add);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray checkedItems = mListView.getCheckedItemPositions();
                int count = mAdapter.getCount();

                for(int i = count - 1 ; i >= 0 ; i--){
                    if(checkedItems.get(i)){
                        lists.get(i).setGroup(args1);
                        Cdh.update(lists.get(i));
                    }
                }
                dismiss();
            }
        });

        return rootView;
    }
}
