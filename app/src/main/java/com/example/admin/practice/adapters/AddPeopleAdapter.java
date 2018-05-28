package com.example.admin.practice.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.admin.practice.ContactsItem;
import com.example.admin.practice.R;

import java.util.ArrayList;

public class AddPeopleAdapter extends BaseAdapter{

    public class CheckItem{
        boolean checked;
        ContactsItem ci;

        public boolean isChecked(){
            return checked;
        }
    }

    private ArrayList<CheckItem> lists = new ArrayList<>();

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public CheckItem getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public boolean isChecked(int position){
        return lists.get(position).checked;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.add_people_list, parent, false);

            CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            TextView tv = (TextView) convertView.findViewById(R.id.tv_name);
            checkBox.setChecked(lists.get(position).checked);
            tv.setText(lists.get(position).ci.getName());
        }else{
            CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            TextView tv = (TextView) convertView.findViewById(R.id.tv_name);
            checkBox.setChecked(lists.get(position).checked);
            tv.setText(lists.get(position).ci.getName());
        }



        return convertView;
    }
    public void addItem(ContactsItem ci){
        CheckItem item = new CheckItem();
        item.checked = false;
        item.ci = ci;
        lists.add(item);
    }

}
