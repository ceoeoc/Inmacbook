package com.example.admin.practice.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.admin.practice.ContactsItem;
import com.example.admin.practice.DB.CIDBHandler;
import com.example.admin.practice.ListViewItem;
import com.example.admin.practice.activites.MainActivity;
import com.example.admin.practice.adapters.ContactsAdapter;
import com.example.admin.practice.R;
import com.github.clans.fab.FloatingActionButton;
import com.wafflecopter.multicontactpicker.MultiContactPicker;

import java.util.ArrayList;
import java.util.List;


public class ContactsFragment extends Fragment {

    private CIDBHandler dh;
    private ListView mListView;
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<>();
    private ContactsAdapter mAdapter;
    private List<ContactsItem> lists;
    private ListViewItem selectedItem;
    private FloatingActionButton cfab,gfab;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contacts_fragment, container, false);
        mListView = (ListView) rootView.findViewById(R.id.listview);

        dh = new CIDBHandler(getActivity());
        dh.open();
        mAdapter = new ContactsAdapter();
        listViewItemList.clear();
        lists = dh.getData(0);
        for(int i = 0 ; i < lists.size();i++){
            listViewItemList.add(new ListViewItem(1,lists.get(i)));
        }
        mAdapter.addItem(listViewItemList);
        mListView.setAdapter(mAdapter);

        cfab = (FloatingActionButton) rootView.findViewById(R.id.cafab);
        cfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MultiContactPicker.Builder(getActivity()) //Activity/fragment context
                        .theme(R.style.MyCustomPickerTheme) //Optional - default: MultiContactPicker.Azure
                        .hideScrollbar(false) //Optional - default: false
                        .showTrack(true) //Optional - default: true
                        .searchIconColor(Color.WHITE) //Option - default: White
                        .setChoiceMode(MultiContactPicker.CHOICE_MODE_MULTIPLE) //Optional - default: CHOICE_MODE_MULTIPLE
                        .handleColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary)) //Optional - default: Azure Blue
                        .bubbleColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary)) //Optional - default: Azure Blue
                        .bubbleTextColor(Color.WHITE) //Optional - default: White
                        .showPickerForResult(MainActivity.CONTACT_PICKER_REQUEST);

            }
        });

        gfab = (FloatingActionButton) rootView.findViewById(R.id.gmfab);
        gfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupManageFragment Gdialog = new GroupManageFragment();
                Gdialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme );
                Gdialog.show(getFragmentManager(),"GroupManageFragment");
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                selectedItem = listViewItemList.get(position);
                if (selectedItem.getType() == 1) {
                    final List<String> listitems = new ArrayList<>();
                    listitems.add("자세히 보기");
                    listitems.add("데이터 제거");
                    listitems.add("호감도 올리기");
                    final CharSequence[] items = listitems.toArray(new String[listitems.size()]);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(selectedItem.getCi().getName());
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String selectedText = items[which].toString();
                            switch (selectedText) {
                                case "자세히 보기":
                                    ContactsInfoDialogFragment Cdialog = ContactsInfoDialogFragment.newInstance(selectedItem.getCi().get_id());
                                    Cdialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);
                                    Cdialog.show(getFragmentManager(), "ContactsInfoDialogFragment");
                                    break;
                                case "데이터 제거":
                                    AlertDialog.Builder removeBuilder = new AlertDialog.Builder(getActivity(), R.style.MyAlterDialogStyle);
                                    removeBuilder.setTitle("Remove");
                                    removeBuilder.setMessage("If you select OK button, All data will be deleted. Please select carefully.");
                                    removeBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            lists.remove(position);
                                            dh.delete(selectedItem.getCi().get_id());
                                            Refresh();
                                            Toast.makeText(getActivity(), "Deleted Data", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    removeBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(getActivity(), "Canceled remove", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    removeBuilder.show();
                                    break;
                                case "호감도 올리기":
                                    selectedItem.getCi().setPoint(selectedItem.getCi().getPoint() + 10);
                                    dh.update(selectedItem.getCi());
                                    Refresh();
                                    break;
                                default:
                                    break;
                            }
                        }
                    });
                    builder.show();

                }
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

