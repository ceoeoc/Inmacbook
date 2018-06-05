package com.example.admin.practice.adapters;

import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

    public static class EAViewHolder{
        TextView tv_title;
        TextView tv_progress;
        TextView tv_date;
    }

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
        final EAViewHolder holder;

        EventItem mitem = eventItems.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.event_list, parent, false);

            holder = new EAViewHolder();
            holder.tv_title = (TextView) convertView.findViewById(R.id.eventname);
            holder.tv_progress = (TextView) convertView.findViewById(R.id.eventprogress);
            holder.tv_date = (TextView) convertView.findViewById(R.id.date);
            convertView.setTag(holder);
        }else{
            holder = (EAViewHolder) convertView.getTag();
        }
        holder.tv_title.setText(mitem.getEventName());
        holder.tv_progress.setText(""+ mitem.getSize());
        holder.tv_date.setText(mitem.getStDate() + " " + mitem.getEndDate());

        return convertView;
    }

    public void addItem(List<EventItem> list){
        for(int i = 0 ; i < list.size();i++){
            eventItems.add(list.get(i));
        }
    }
}
