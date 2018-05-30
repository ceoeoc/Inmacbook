package com.example.admin.practice.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.admin.practice.ContactsItem;
import com.example.admin.practice.DB.CIDBHandler;
import com.example.admin.practice.R;
import com.example.admin.practice.adapters.AddPeopleAdapter;

import java.util.ArrayList;
import java.util.List;

public class AddPeople extends DialogFragment {
    public static final String TAG = "AddPeopleFragment";
    private static final String ARG_PARA1 = "para1";
    private static final String ARG_PARA2 = "para2";


    private AddPeopleAdapter mAdapter;
    private ListView mListView;
    private ImageButton cB;
    private List<ContactsItem> lists;
    private String args1,args2;
    private CIDBHandler Cdh;

    public static AddPeople newInstance(String para1, String para2){
        AddPeople f = new AddPeople();
        Bundle args = new Bundle();
        args.putString(ARG_PARA1,para1);
        args.putString(ARG_PARA2,para2);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            args1 = getArguments().getString(ARG_PARA1);
            args2 = getArguments().getString(ARG_PARA2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_people, container, false);
        Cdh = new CIDBHandler(getActivity());
        Cdh.open();

        cB = (ImageButton) rootView.findViewById(R.id.cancel);
        cB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        mListView = (ListView) rootView.findViewById(R.id.listview);
        mAdapter = new AddPeopleAdapter();
        switch (args1) {
            case "add":
                mListView.setAdapter(mAdapter);

                lists = Cdh.getGroupData("unknown");
                for (int i = 0; i < lists.size(); i++) {
                    mAdapter.addItem(lists.get(i));
                }

                Button addbtn = (Button) rootView.findViewById(R.id.addpeoplebtn);
                addbtn.setText("선택된 사람 추가");
                addbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SparseBooleanArray checkedItems = mListView.getCheckedItemPositions();
                        int count = mAdapter.getCount();
                        for (int i = count - 1; i >= 0; i--) {
                            if (checkedItems.get(i)) {
                                lists.get(i).setGroup(args2);
                                Cdh.update(lists.get(i));
                            }
                        }
                        getDialog().dismiss();
                    }
                });

            break;
            case "minus":
                mListView.setAdapter(mAdapter);
                lists = Cdh.getGroupData(args2);
                for (int i = 0; i < lists.size(); i++) {
                    mAdapter.addItem(lists.get(i));
                }
                Button mibtn = (Button) rootView.findViewById(R.id.addpeoplebtn);
                mibtn.setText("선택된 사람 제거");
                mibtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SparseBooleanArray checkedItems = mListView.getCheckedItemPositions();
                        int count = mAdapter.getCount();
                        for (int i = count - 1; i >= 0; i--) {
                            if (checkedItems.get(i)) {
                                lists.get(i).setGroup("unknown");
                                Cdh.update(lists.get(i));
                            }
                        }
                        getDialog().dismiss();
                    }
                });
            break;
        }


        return rootView;
    }
}
