package com.example.admin.practice.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.admin.practice.DB.EVDBHandler;
import com.example.admin.practice.EventItem;
import com.example.admin.practice.R;
import com.example.admin.practice.activites.MainActivity;

import java.util.ArrayList;

public class EventMakeDialogFragment extends DialogFragment {
    EVDBHandler edh;
    Button TxtStartDate,TxtEndDate,TimeBtn,AddPersonBtn;
    EditText nametxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.event_make, container, false);
        edh = new EVDBHandler(getActivity());
        edh.open();

        final Calendar c = Calendar.getInstance();
        final int cyear = c.get(Calendar.YEAR);
        final int cmonth = c.get(Calendar.MONTH);
        final int cday = c.get(Calendar.DAY_OF_MONTH);
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);

        nametxt = (EditText)rootView.findViewById(R.id.eventname);

        TxtStartDate = (Button)rootView.findViewById(R.id.startDateButton);
        TxtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), R.style.MyAlterDialogStyle ,listener1, cyear,cmonth,cday);
                dialog.show();

            }
        });

        TxtEndDate = (Button)rootView.findViewById(R.id.endDateButton);
        TxtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), R.style.MyAlterDialogStyle ,listener2, cyear,cmonth,cday);
                dialog.show();
            }
        });

        TimeBtn = (Button) rootView.findViewById(R.id.timeMeasure);
        TimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(getActivity(),R.style.MyAlterDialogStyle,listener3,hour,minute, DateFormat.is24HourFormat(getActivity()));
                dialog.show();
            }
        });

        AddPersonBtn = (Button) rootView.findViewById(R.id.SelectPersonButton);
        AddPersonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPeople Adialog = AddPeople.newInstance("addlist","");
                Adialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme );
                Adialog.show(getActivity().getFragmentManager(),"AddPeopleFragment");
                Adialog.setDialogResult(new AddPeople.OnMyDialogResult() {
                    @Override
                    public void finish(ArrayList<String> list) {
                        EventItem item = new EventItem();
                        item.setEventId(MainActivity.EID);
                        item.setStDate(TxtStartDate.getText().toString());
                        item.setEndDate(TxtEndDate.getText().toString());
                        item.setEventName(nametxt.getText().toString());
                        item.setHour(TimeBtn.getText().toString());
                        item.setProgress("0");
                        item.setMemberCid(list);
                        edh.insert(item);
                        MainActivity.EID ++;
                        MainActivity.setIntPref(getActivity(),"EID",MainActivity.EID);
                        getDialog().dismiss();
                    }
                });
            }
        });

        Button cancel = (Button) rootView.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return rootView;
    }

    private DatePickerDialog.OnDateSetListener listener1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            TxtStartDate.setText(String.format("%d/%d/%d", year, monthOfYear + 1, dayOfMonth));
        }
    };

    private DatePickerDialog.OnDateSetListener listener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            TxtEndDate.setText(String.format("%d/%d/%d", year, monthOfYear + 1, dayOfMonth));
        }
    };

    private TimePickerDialog.OnTimeSetListener listener3 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            TimeBtn.setText((String.format("%d%d",hourOfDay,minute)));
        }
    };
}
