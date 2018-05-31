package com.example.admin.practice.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.admin.practice.R;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class EventMakeDialogFragment extends DialogFragment {

    Button TxtStartDate,TxtEndDate,TimeBtn,AddPersonBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.event_make, container, false);

        Calendar c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH);
        int cday = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

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

        AddPersonBtn = (Button) rootView.findViewById(R.id.selectPersonButton);
        AddPersonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPeople Adialog = AddPeople.newInstance("addlist","");
                Adialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme );
                Adialog.show(getActivity().getFragmentManager(),"AddPeopleFragment");
            }
        });

        return rootView
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
