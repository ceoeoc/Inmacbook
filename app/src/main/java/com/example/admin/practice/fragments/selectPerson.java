package com.example.admin.practice.fragments;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.admin.practice.DB.CIDBHandler;
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

    public void onSelectPerson(View v)
    {
        AddPeople Adialog = AddPeople.newInstance("addlist","mola");
        Adialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme );
        Adialog.show(getFragmentManager(),"AddPeopleFragment");

        final List<String> ListItems = new ArrayList<>();
        ListItems.add("사과");    //친구목록 불러와서 해야댈듯
        ListItems.add("배");
        ListItems.add("귤");
        ListItems.add("바나나");
        final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);

        final List SelectedItems  = new ArrayList();


        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.MyAlterDialogStyle);
        builder.setTitle("AlertDialog Title");
        builder.setMultiChoiceItems(items, null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {
                        if (isChecked) {
                            //사용자가 체크한 경우 리스트에 추가
                            SelectedItems.add(which);
                        } else if (SelectedItems.contains(which)) {
                            //이미 리스트에 들어있던 아이템이면 제거
                            SelectedItems.remove(Integer.valueOf(which));
                        }
                    }
                });
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String msg="";
                        for (int i = 0; i < SelectedItems.size(); i++) {
                            int index = (int) SelectedItems.get(i);

                            msg=msg+"\n"+(i+1)+" : " +ListItems.get(index);
                        }
                    }
                });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

}
