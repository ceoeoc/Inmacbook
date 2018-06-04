package com.example.admin.practice.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.practice.ContactsItem;
import com.example.admin.practice.DB.CIDBHandler;
import com.example.admin.practice.DB.EVDBHandler;
import com.example.admin.practice.EventItem;
import com.example.admin.practice.LogObject;
import com.example.admin.practice.LogsManager;
import com.example.admin.practice.R;
import com.example.admin.practice.activites.MainActivity;
import com.example.admin.practice.adapters.ContactsAdapter;
import com.example.admin.practice.adapters.LogsAdapter;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class EventInfoDialogFragment extends DialogFragment {
    public static final String TAG = "EventInfoDialogFragment";
    private static final String Key = "EventInfomation";

    private CIDBHandler cdh;
    private EVDBHandler edh;
    private EventItem mitem;
    private ContactsAdapter mAdapter;
    private ListView mListView;


    private int meid;

    public static EventInfoDialogFragment newInstance (int eid){
        EventInfoDialogFragment f = new EventInfoDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Key,eid);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            meid = getArguments().getInt(Key);
        }
    }
    @RequiresPermission(Manifest.permission.READ_CALL_LOG)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.event_info, container, false);

        edh = new EVDBHandler(getActivity());
        edh.open();
        cdh = new CIDBHandler(getActivity());
        cdh.open();

        mitem = edh.getData(meid);
        mListView = (ListView)rootView.findViewById(R.id.members);
        mAdapter = new ContactsAdapter();
        List<EventItem.MperP> lists = mitem.getMembers();
        for(int i = 0 ; i < lists.size();i++){
            mAdapter.addItem(2,lists.get(i).getMemP(),cdh.getNameByID(lists.get(i).getMemberCid()),null);
        }
        mListView.setAdapter(mAdapter);

        TextView tv_ename = (TextView) rootView.findViewById(R.id.tv_name);
        tv_ename.setText(mitem.getEventName());
        TextView tv_st = (TextView) rootView.findViewById(R.id.tv_st);
        tv_st.setText(mitem.getStDate());
        TextView tv_ed = (TextView) rootView.findViewById(R.id.tv_end);
        tv_ed.setText(mitem.getEndDate());
        TextView tv_hour = (TextView) rootView.findViewById(R.id.tv_hour);
        tv_hour.setText(mitem.getHour());

        return rootView;
    }
}
