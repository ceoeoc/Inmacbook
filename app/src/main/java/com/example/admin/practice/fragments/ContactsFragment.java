package com.example.admin.practice.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.admin.practice.ContactsItem;
import com.example.admin.practice.DBHandler;
import com.example.admin.practice.adapters.ContactsAdapter;
import com.example.admin.practice.R;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;


public class ContactsFragment extends Fragment {

    private DBHandler dh;
    private ListView mListView;
    private List<ContactsItem> lists;
    private ContactsAdapter mAdapter;
    private ContactsItem selectedContacts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contacts_fragment, container, false);
        mListView = (ListView) rootView.findViewById(R.id.listview);

        mAdapter = new ContactsAdapter();

        dh = new DBHandler(getActivity());
        dh.open();
        lists = dh.getData();

        for(int i = 0 ; i < lists.size() ; i++){
            mAdapter.addItem(lists.get(i));
        }
        mListView.setAdapter(mAdapter);
        /*
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedContacts = lists.get(position);
                final List<String> listitems = new ArrayList<>();
                listitems.add("즐겨찾기");
                listitems.add("자세히 보기");
                listitems.add("데이터 제거");
                listitems.add("호감도 올리기");
                final CharSequence[] items = listitems.toArray(new String[listitems.size()]);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(selectedContacts.getName());
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedText = items[which].toString();
                        switch(selectedText){
                            case "즐겨찾기":
                                Toast.makeText(getActivity(),"Add to Favorite list", Toast.LENGTH_SHORT).show();
                                break;
                            case "자세히 보기":
                                ContactsInfoDialogFragment Cdialog = ContactsInfoDialogFragment.newInstance(selectedContacts.get_id());
                                Cdialog.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light );
                                Cdialog.show(getFragmentManager(),"ContactsInfoDialogFragment");

                                break;
                            case "데이터 제거":
                                AlertDialog.Builder removeBuilder = new AlertDialog.Builder(getActivity());
                                removeBuilder.setTitle("Remove");
                                removeBuilder.setMessage("If you select OK button, All data will be deleted. Please select carefully.");
                                removeBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dh.delete(selectedContacts.get_id());
                                        Refresh();
                                        Toast.makeText(getActivity(),"Deleted Data", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                removeBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getActivity(),"Canceled remove", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                removeBuilder.show();
                                break;
                            case "호감도 올리기":
                                selectedContacts.setPoint(selectedContacts.getPoint() + 10);
                                dh.update(selectedContacts);
                                Refresh();
                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.show();

                return false;
            }
        });

        return rootView;
    }

    public void Refresh(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }


}

