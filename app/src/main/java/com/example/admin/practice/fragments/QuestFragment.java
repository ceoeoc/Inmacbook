package com.example.admin.practice.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.admin.practice.R;
import com.github.clans.fab.FloatingActionButton;

public class QuestFragment extends Fragment{
    private Button button;
    private FloatingActionButton eafab,emfab;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.event_fragment, container, false);

        eafab = (FloatingActionButton) rootView.findViewById(R.id.eafab);
        eafab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),EventMakeDialogFragment.class);
                startActivity(intent);
            }
        });
        emfab = (FloatingActionButton) rootView.findViewById(R.id.emfab);

        return rootView;
    }
}
