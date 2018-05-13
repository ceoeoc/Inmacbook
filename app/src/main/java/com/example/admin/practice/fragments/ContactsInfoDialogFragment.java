package com.example.admin.practice.fragments;

<<<<<<< HEAD
import android.Manifest;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
=======
import android.os.Bundle;
>>>>>>> 1164e981a013830d39a780e74ea8194195ef7c36
import android.support.v4.app.DialogFragment;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.admin.practice.CallLogObject;
import com.example.admin.practice.ContactsItem;
import com.example.admin.practice.DBHandler;
import com.example.admin.practice.LogObject;
import com.example.admin.practice.LogsManager;
import com.example.admin.practice.R;
import com.example.admin.practice.adapters.LogsAdapter;

import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class ContactsInfoDialogFragment extends DialogFragment {
    public static final String TAG = "ContactsInfoDialogFragment";
    private static final String Key = "ContactsInformation";

    private DBHandler dh;
    private ContactsItem mitem;

    private List<LogObject> callLogs;
    private WebView mcl;
    private Button oB,cB;
    private EditText name;
    private TextView phone;
    private Spinner gsp;
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
<<<<<<< HEAD
    @RequiresPermission(Manifest.permission.READ_CALL_LOG)
=======

>>>>>>> 1164e981a013830d39a780e74ea8194195ef7c36
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contacts_info, container, false);

        dh = new DBHandler(getActivity());
        dh.open();
        mitem = dh.getData(mcid);

        name = (EditText) rootView.findViewById(R.id.name);
        phone = (TextView) rootView.findViewById(R.id.phone);
        name.setText(mitem.getName());
        name.setClickable(false);
        name.setFocusable(false);
        name.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                name.setFocusableInTouchMode(true);
                name.setClickable(true);
                name.setFocusable(true);
                return false;
            }
        });
        phone.setText(mitem.getPhone());

        gsp = rootView.findViewById(R.id.gspinner);
        setSpinner();
        long subTime = 1000 * 60 * 60 * 24 * -1;
        long date = System.currentTimeMillis() + 7 * subTime;

        ListView mlv = (ListView) rootView.findViewById(R.id.cllv);
        LogsManager logsManager = new LogsManager(getActivity());
<<<<<<< HEAD
        try {
            callLogs = logsManager.getLogs(LogsManager.ALL_CALLS, mitem.getPhone(), date);
        }catch (SecurityException e){
            e.printStackTrace();
        }
        callLogs = new ArrayList<>();
=======
        callLogs = logsManager.getLogs(LogsManager.ALL_CALLS,mitem.getPhone(),date);
>>>>>>> 1164e981a013830d39a780e74ea8194195ef7c36
        LogsAdapter logsAdapter = new LogsAdapter(getActivity(),R.layout.log_layout,callLogs);
        mlv.setAdapter(logsAdapter);

        oB = (Button) rootView.findViewById(R.id.confrim);
        cB = (Button) rootView.findViewById(R.id.cancel);

        oB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mitem.setName(name.getText().toString());
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
        ArrayAdapter groupAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.group , android.R.layout.simple_spinner_item);
        groupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gsp.setAdapter(groupAdapter);
        switch(mitem.getGroup()){
            case "가족":
                gsp.setSelection(0);
                break;
            case "친구":
                gsp.setSelection(1);
                break;
            case "타인":
                gsp.setSelection(2);
                break;
            case "unknown":
                gsp.setSelection(3);
                break;
        }
        gsp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGroup = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
