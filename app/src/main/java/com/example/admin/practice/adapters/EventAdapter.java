package com.example.admin.practice.adapters;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.practice.EventItem;
import com.example.admin.practice.ListViewItem;
import com.example.admin.practice.R;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends BaseAdapter {

    private ArrayList<EventItem> eventItems = new ArrayList<EventItem>();

    @Override
    public int getCount() {
        return eventItems.size();
    }

    @Override
    public EventItem getItem(int position) {
        return eventItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        EventItem mitem = eventItems.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.event_list, parent, false);
            TextView tv_title = (TextView) convertView.findViewById(R.id.eventname);
            tv_title.setText(mitem.getEventName());
            TextView tv_progess = (TextView) convertView.findViewById(R.id.eventprogress);
            tv_progess.setText(mitem.getProgress());
            TextView tv_date = (TextView) convertView.findViewById(R.id.date);
            tv_date.setText(mitem.getStDate() + " " + mitem.getEndDate());
        }


        return convertView;
    }

    public void addItem(List<EventItem> list){
        for(int i = 0 ; i < list.size();i++){
            eventItems.add(list.get(i));
        }
    }
}
