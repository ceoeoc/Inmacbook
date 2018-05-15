package com.example.admin.practice.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.admin.practice.R;
import com.example.admin.practice.SelectPosition;

import java.util.ArrayList;
import java.util.List;

public class eventMake extends AppCompatActivity {

    TextView TxtDate;
    NumberPicker Picker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_make);
        onMeasureHour();
    }

    public void onSelectPersonClicked(View v){

        Intent intent = new Intent(getApplicationContext(),SelectPosition.class);
        startActivity(intent);
    }

    public void onBackButtonClicked(View v){
        finish();
    }

    public void onStartEventClicked(View v){
        Calendar c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH);
        int cday = c.get(Calendar.DAY_OF_MONTH);

        TxtDate = (TextView)findViewById(R.id.startDateButton);
        DatePickerDialog dialog = new DatePickerDialog(this, listener1, cyear,cmonth,cday);
        dialog.show();
    }

    public void onEndEventClicked(View v){
        Calendar c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH);
        int cday = c.get(Calendar.DAY_OF_MONTH);

        TxtDate = (TextView)findViewById(R.id.endDateButton);
        DatePickerDialog dialog = new DatePickerDialog(this, listener2, cyear,cmonth,cday);
        dialog.show();
    }

    private DatePickerDialog.OnDateSetListener listener1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            TxtDate.setText(String.format("%d/%d/%d", year, monthOfYear + 1, dayOfMonth));
        }
    };

    private DatePickerDialog.OnDateSetListener listener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            TxtDate.setText(String.format("%d/%d/%d", year, monthOfYear + 1, dayOfMonth));
        }
    };


    public void onMeasureHour() {
        Picker=(NumberPicker)findViewById(R.id.timeMeasure);
        Picker.setMaxValue(120);
        Picker.setMinValue(0);
        Picker.setWrapSelectorWheel(true);
        Picker.setValue(3);
    }

}
