package com.example.admin.practice.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.practice.ContactsItem;
import com.example.admin.practice.DB.CIDBHandler;
import com.example.admin.practice.ListViewItem;
import com.example.admin.practice.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class GroupAdapter extends BaseAdapter {
    private static final int ITEM_TITLE = 0;
    private static final int ITEM_GROUP = 1;
    private static final int ITEM_TYPE_MAX = 2;

    private ArrayList<ListViewItem> listViewItems = new ArrayList<>();

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
        return listViewItems.size();
    }

    @Override
    public ListViewItem getItem(int position) {
        return listViewItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Context context = parent.getContext();
        int viewType = getItemViewType(position);
        ListViewItem listViewItem = listViewItems.get(position);
        switch (viewType) {
            case ITEM_TITLE:
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.subtitle, parent, false);
                    TextView tv_title = (TextView) convertView.findViewById(R.id.subtitle);
                    tv_title.setText(listViewItem.getTitleStr());
                }
                break;
            case ITEM_GROUP:
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.group_list, parent, false);
                    TextView tv_gn = (TextView) convertView.findViewById(R.id.groupname);
                    TextView tv_sz = (TextView) convertView.findViewById(R.id.groupnumber);
                    tv_gn.setText(listViewItem.getTitleStr());
                    tv_sz.setText("" + listViewItem.getSz());
                }
                break;
        }
        return convertView;
    }

    public Bitmap getAppIcon(byte[] b){
        Bitmap bm = BitmapFactory.decodeByteArray(b,0,b.length);
        return bm;
    }
    public void addItem(List<ListViewItem> list){
        for(int i = 0 ; i < list.size();i++){
            listViewItems.add(list.get(i));
        }
    }
    public void addItem(int type, int sz, String str, ContactsItem ci){
        ListViewItem item = new ListViewItem();
        switch(type) {
            case ITEM_TITLE:
                item.setType(ITEM_TITLE);
                item.setStr(str);
                break;
            case ITEM_GROUP:
                item.setType(ITEM_GROUP);
                item.setSz(sz);
                item.setStr(str);
                break;
        }
        listViewItems.add(item);
    }

    public void clear(){
        listViewItems.clear();
    }
}
