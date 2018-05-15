package com.example.admin.practice.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.admin.practice.DBHandler;
import com.example.admin.practice.R;

import java.util.ArrayList;
import java.util.List;

public class selectPerson extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_person);
    }

    public void onBackButtonClicked(View v){
        finish();
    }

    public void onSelectPerson(View v) {

    }

}
