package com.example.admin.practice.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin.practice.ContactsItem;
import com.example.admin.practice.R;

import java.util.ArrayList;

public class ContactsAdapter extends BaseAdapter {

    private ArrayList<ContactsItem> mItems = new ArrayList<>();

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public ContactsItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.contacts_list, parent, false);
        }

        ImageView iv_img = (ImageView) convertView.findViewById(R.id.iv_img) ;
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name) ;
        TextView tv_contents = (TextView) convertView.findViewById(R.id.tv_contents) ;
        ProgressBar pgb = (ProgressBar) convertView.findViewById(R.id.pgb);

        ContactsItem myItem = getItem(position);

        iv_img.setImageDrawable(myItem.getIcon());
        tv_name.setText(myItem.getName());
        tv_contents.setText(myItem.getPhone());
        pgb.setProgress(myItem.getPoint());

        return convertView;
    }

    public void addItem(Drawable img, String name, String phone, int point) {

        ContactsItem mItem = new ContactsItem();

        mItem.setIcon(img);
        mItem.setName(name);
        mItem.setPhone(phone);
        mItem.setPoint(point);

        mItems.add(mItem);
    }
}
