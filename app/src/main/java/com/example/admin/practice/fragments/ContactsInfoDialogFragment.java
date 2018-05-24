package com.example.admin.practice.fragments;

import android.Manifest;
import android.content.Context;
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

import com.example.admin.practice.ContactsItem;
import com.example.admin.practice.DB.CIDBHandler;
import com.example.admin.practice.LogObject;
import com.example.admin.practice.LogsManager;
import com.example.admin.practice.R;
import com.example.admin.practice.activites.MainActivity;
import com.example.admin.practice.adapters.LogsAdapter;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

public class ContactsInfoDialogFragment extends DialogFragment {
    public static final String TAG = "ContactsInfoDialogFragment";
    private static final String Key = "ContactsInformation";

    private CIDBHandler dh;
    private ContactsItem mitem;

    private List<LogObject> callLogs;
    private InputMethodManager imm;
    private ImageButton oB,cB;
    private ImageView eN,eP;
    private EditText name, phone;
    private MaterialSpinner gsp;
    private String mcid;
    private String selectedGroup;

    public static ContactsInfoDialogFragment newInstance (String cid){
        ContactsInfoDialogFragment f = new ContactsInfoDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Key,cid);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mcid = getArguments().getString(Key);
        }
    }
    @RequiresPermission(Manifest.permission.READ_CALL_LOG)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contacts_info, container, false);
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        dh = new CIDBHandler(getActivity());
        dh.open();
        mitem = dh.getData(mcid);

        name = (EditText) rootView.findViewById(R.id.name);
        name.setText(mitem.getName());
        name.setClickable(false);
        name.setFocusable(false);
        eN = (ImageView) rootView.findViewById(R.id.ed_name);
        eN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setFocusableInTouchMode(true);
                name.setClickable(true);
                name.setFocusable(true);
                imm.showSoftInput(name,0);
                oB.setEnabled(true);
            }
        });
        phone = (EditText) rootView.findViewById(R.id.phone);
        phone.setText(mitem.getPhone());
        phone.setClickable(false);
        phone.setFocusable(false);
        eP = (ImageView) rootView.findViewById(R.id.ed_phone);
        eP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone.setFocusableInTouchMode(true);
                phone.setClickable(true);
                phone.setFocusable(true);
                imm.showSoftInput(phone,0);
                oB.setEnabled(true);
            }
        });

        gsp = (MaterialSpinner) rootView.findViewById(R.id.gspinner);
        setSpinner();
        long subTime = 1000 * 60 * 60 * 24 * -1;
        long date = System.currentTimeMillis() + 7 * subTime;

        ListView mlv = (ListView) rootView.findViewById(R.id.cllv);
        LogsManager logsManager = new LogsManager(getActivity());
        try {
            callLogs = new ArrayList<LogObject>();
            callLogs = logsManager.getLogs(LogsManager.ALL_CALLS, mitem.getPhone(), date);
        }catch (SecurityException e){
            e.printStackTrace();
        }

        LogsAdapter logsAdapter = new LogsAdapter(getActivity(),R.layout.log_layout,callLogs);
        mlv.setAdapter(logsAdapter);

        oB = (ImageButton) rootView.findViewById(R.id.confrim);
        oB.setEnabled(false);
        cB = (ImageButton) rootView.findViewById(R.id.cancel);

        oB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mitem.setName(name.getText().toString());
                mitem.setPhone(phone.getText().toString());
                if(!selectedGroup.isEmpty()){
                    mitem.setGroup(selectedGroup);
                }
                dh.update(mitem);
                getDialog().dismiss();
            }
        });

        cB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return rootView;
    }

    public void setSpinner(){
        MainActivity.groups.add("정의 되지 않음");
        gsp.setItems(MainActivity.groups);
        gsp.setSelectedIndex(MainActivity.groups.size()-1);
        for(int i=0;i<MainActivity.groups.size();i++) {
            if(mitem.getGroup() == MainActivity.groups.get(i)) {
                gsp.setSelectedIndex(i);
            }
        }
        MainActivity.groups.remove(MainActivity.groups.size()-1);
        gsp.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                if(item != "정의 되지 않음") {
                    selectedGroup = item;
                    oB.setEnabled(true);
                }
            }
        });
    }
}
