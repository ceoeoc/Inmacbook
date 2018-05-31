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
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.practice.ContactEvent;
import com.example.admin.practice.R;
import com.example.admin.practice.activites.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class eventMake extends AppCompatActivity {

    TextView TxtDate;
    ContactEvent event;
    String title,startDate,endDate;
    int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_make);

        NumberPicker np = findViewById(R.id.timeMeasure);
        EditText text =(EditText) findViewById(R.id.editText3);

        np.setMinValue(1);
        np.setMaxValue(100);
        np.setOnValueChangedListener(onValueChangeListener);
    }

    public void inputTitle(View v) {
        EditText text =(EditText) findViewById(R.id.editText3);
        title=text.getText().toString();
    }

    NumberPicker.OnValueChangeListener onValueChangeListener = new 	NumberPicker.OnValueChangeListener(){
        @Override
        public void onValueChange(NumberPicker numberPicker, int i, int i1) {
            time=numberPicker.getValue();
        }
    };

    public void onSelectPersonClicked(View v){
        event.set_title(title);
        event.set_start(startDate);
        event.set_start(endDate);
        event.set_time(time);
        Intent intent = new Intent(getApplicationContext(),selectPerson.class);
        startActivity(intent);
    }

    public void onBackButtonClicked(View v){
        finish();
    }

    // 시작 날짜 선택
    public void onStartEventClicked(View v){
        Calendar c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH);
        int cday = c.get(Calendar.DAY_OF_MONTH);

        TxtDate = (TextView)findViewById(R.id.startDateButton);
        DatePickerDialog dialog = new DatePickerDialog(this, listener1, cyear,cmonth,cday);
        dialog.show();
    }

    // 종료 날짜 선택
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
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
            TxtDate.setText(String.format("%d/%d/%d", year, monthOfYear + 1, dayOfMonth));
            startDate = String.format("%d/%d/%d", year, monthOfYear + 1, dayOfMonth);
        }
    };

    private DatePickerDialog.OnDateSetListener listener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            TxtDate.setText(String.format("%d/%d/%d", year, monthOfYear + 1, dayOfMonth));
            endDate = String.format("%d/%d/%d", year, monthOfYear + 1, dayOfMonth);
        }
    };

}
