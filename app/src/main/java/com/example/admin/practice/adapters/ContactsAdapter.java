package com.example.admin.practice.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin.practice.ContactsItem;
import com.example.admin.practice.ListViewItem;
import com.example.admin.practice.R;

import java.util.ArrayList;

public class ContactsAdapter extends BaseAdapter {

    private static final int ITEM_TITLE = 0;
    private static final int ITEM_GROUP = 1;
    private static final int ITEM_CONTACT = 2;
    private static final int ITEM_TYPE_MAX = 3;

    private ArrayList<ListViewItem> listViewItems = new ArrayList<>();
    private ArrayList<ContactsItem> mItems = new ArrayList<>();
    private ArrayList<String> groups = new ArrayList<>();

    @Override
    public int getViewTypeCount(){
        return ITEM_TYPE_MAX;
    }

    @Override
    public int getItemViewType(int position){
        return listViewItems.get(position).getType();
    }

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
        int viewType = getItemViewType(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            ListViewItem listViewItem = listViewItems.get(position);
            //아이템별로 스위치 나누고 스위치별로 레이아웃 따로 만들어서 설정해줄것
            switch (viewType) {
                case ITEM_TITLE:
                convertView = inflater.inflate(R.layout.contacts_list, parent, false);
            }
        }

        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        ProgressBar pgb = (ProgressBar) convertView.findViewById(R.id.pgb);
        ImageView iv_image = (ImageView) convertView.findViewById(R.id.iv_image);

        ContactsItem myItem = getItem(position);

        tv_name.setText(myItem.getName());
        pgb.setProgress(myItem.getPoint());

        return convertView;
    }

    public Bitmap getAppIcon(byte[] b){
        Bitmap bm = BitmapFactory.decodeByteArray(b,0,b.length);
        return bm;
    }

    public void addItem(ContactsItem mItem) {
        mItems.add(mItem);
    }
    public void addItem(String groupname){ groups.add(groupname);}
}
