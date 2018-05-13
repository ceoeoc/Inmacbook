package com.example.admin.practice.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.admin.practice.R;

public class QuestFragment extends Fragment{
<<<<<<< HEAD:app/src/main/java/com/example/admin/practice/fragments/QuestFragment.java
    Button button;
=======
>>>>>>> 1164e981a013830d39a780e74ea8194195ef7c36:app/src/main/java/com/example/admin/practice/fragments/QuestFragment.java
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2_fragment, container, false);
        button = (Button)rootView.findViewById(R.id.addEvent);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),eventMake.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
