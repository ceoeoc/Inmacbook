package com.example.admin.practice.fragments;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.DialogFragment;
<<<<<<< HEAD
=======
import android.util.TypedValue;
>>>>>>> develop-park
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.admin.practice.ContactsItem;
import com.example.admin.practice.DBHandler;
import com.example.admin.practice.LogObject;
import com.example.admin.practice.LogsManager;
import com.example.admin.practice.R;
import com.example.admin.practice.adapters.LogsAdapter;

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
    private TextView name, phone;
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
    @RequiresPermission(Manifest.permission.READ_CALL_LOG)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contacts_info, container, false);

        dh = new DBHandler(getActivity());
        dh.open();
        mitem = dh.getData(mcid);

        name = (TextView) rootView.findViewById(R.id.name);
        phone = (TextView) rootView.findViewById(R.id.phone);
        name.setText(mitem.getName());
        phone.setText(mitem.getPhone());

        gsp = rootView.findViewById(R.id.gspinner);
        gsp.setScrollBarSize((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,60.0f,getContext().getResources().getDisplayMetrics()));
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
                final View v = gsp.getSelectedView();
                ((TextView) v).setTextSize(30);
                ((TextView) v).setTextColor(R.color.colorPrimary);
                selectedGroup = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
