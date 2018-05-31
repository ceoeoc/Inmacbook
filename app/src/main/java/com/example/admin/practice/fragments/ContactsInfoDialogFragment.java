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
    private ImageButton oB,cB;
    private ImageButton eN,eP;
    private TextView name, phone, group;
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

        dh = new CIDBHandler(getActivity());
        dh.open();
        mitem = dh.getData(mcid);

        name = (TextView) rootView.findViewById(R.id.name);
        name.setText(mitem.getName());
        eN = (ImageButton) rootView.findViewById(R.id.ed_name);
        eN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText et = new EditText(getActivity());
                et.setHint(mitem.getName());
                AlertDialog.Builder EditBuilder = new AlertDialog.Builder(getActivity(),R.style.MyAlterDialogStyle);
                EditBuilder.setTitle("이름 수정");
                EditBuilder.setMessage("바꿀 이름을 적어주세요.");
                EditBuilder.setView(et);
                EditBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mitem.setName(et.getText().toString());
                                name.setText(et.getText().toString());
                            }
                        });
                EditBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                EditBuilder.show();
            }
        });
        phone = (TextView) rootView.findViewById(R.id.phone);
        phone.setText(mitem.getPhone());
        eP = (ImageButton) rootView.findViewById(R.id.ed_phone);
        eP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText et = new EditText(getActivity());
                et.setHint(mitem.getPhone());
                AlertDialog.Builder EditBuilder = new AlertDialog.Builder(getActivity(),R.style.MyAlterDialogStyle);
                EditBuilder.setTitle("번호 수정");
                EditBuilder.setMessage("바꿀 번호을 적어주세요.");
                EditBuilder.setView(et);
                EditBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mitem.setPhone(et.getText().toString());
                                phone.setText(et.getText().toString());
                            }
                        });
                EditBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                EditBuilder.show();
            }
        });

        group = (TextView) rootView.findViewById(R.id.group);
        group.setText(mitem.getGroup());
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
}
