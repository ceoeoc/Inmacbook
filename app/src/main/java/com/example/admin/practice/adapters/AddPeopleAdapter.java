package com.example.admin.practice.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.admin.practice.CheckableLinearLayout;
import com.example.admin.practice.ContactsItem;
import com.example.admin.practice.R;

import java.util.ArrayList;

public class AddPeopleAdapter extends BaseAdapter{

    private ArrayList<ContactsItem> lists = new ArrayList<>();

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public ContactsItem getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        Context context = parent.getContext();

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.add_people_list, parent, false);
        }
        final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox1);

        TextView tv = (TextView) convertView.findViewById(R.id.tv_name);
        tv.setText(lists.get(pos).getName());

        return convertView;
    }

    public void addItem(ContactsItem ci){
        lists.add(ci);
    }

}
