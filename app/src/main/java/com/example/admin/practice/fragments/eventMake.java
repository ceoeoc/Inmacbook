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
import android.widget.TextView;

import com.example.admin.practice.R;

import java.util.ArrayList;
import java.util.List;

public class eventMake extends AppCompatActivity {

    TextView TxtDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_make);
    }

    public void onSelectPersonClicked(View v){

        Intent intent = new Intent(getApplicationContext(),selectPerson.class);
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

    public void onMeasureHourClicked(View v)
    {
        final List<String> ListItems = new ArrayList<>();
        ListItems.add("1hour");
        ListItems.add("2hour");
        ListItems.add("3hour");
        ListItems.add("4hour");
        ListItems.add("5hour");
        ListItems.add("6hour");
        ListItems.add("7hour");
        ListItems.add("8hour");
        final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);

        final List SelectedItems  = new ArrayList();
        int defaultItem = 0;
        SelectedItems.add(defaultItem);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("AlertDialog Title");
        builder.setSingleChoiceItems(items, defaultItem,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SelectedItems.clear();
                        SelectedItems.add(which);
                    }
                });
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String msg="";

                        if (!SelectedItems.isEmpty()) {
                            int index = (int) SelectedItems.get(0);
                            msg = ListItems.get(index);
                        }
                    }
                });
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }

}
