package com.example.admin.practice.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.DialogFragment;

import com.example.admin.practice.DB.CIDBHandler;
import com.example.admin.practice.R;
import com.example.admin.practice.activites.MainActivity;

import java.lang.String;
import java.util.ArrayList;

public class GroupManageFragment extends DialogFragment {
    public static final String TAG = "GroupManageFragment";
    private static final String Key = "GroupManage";

    private ArrayList<String> groups;
    private CIDBHandler Cdh;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.group_fragment, container, false);
        groups = MainActivity.groups;
        return rootView;
    }
}
