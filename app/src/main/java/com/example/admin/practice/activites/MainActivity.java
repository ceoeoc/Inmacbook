package com.example.admin.practice.activites;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;

import com.example.admin.practice.ContactsItem;
import com.example.admin.practice.DBHandler;
import com.example.admin.practice.fragments.ContactsFragment;
import com.example.admin.practice.R;
import com.example.admin.practice.fragments.QuestFragment;
import com.example.admin.practice.fragments.StaticsFragment;
import com.example.admin.practice.fragments.RankingFragment;
import com.wafflecopter.multicontactpicker.ContactResult;
import com.wafflecopter.multicontactpicker.MultiContactPicker;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    DBHandler dh = null;
    Fragment frg = null;
    private static final int CONTACT_PICKER_REQUEST = 991;
    FloatingActionButton fab;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dh = new DBHandler(this);
        dh.open();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MultiContactPicker.Builder(MainActivity.this) //Activity/fragment context
                        .theme(R.style.MyCustomPickerTheme) //Optional - default: MultiContactPicker.Azure
                        .hideScrollbar(false) //Optional - default: false
                        .showTrack(true) //Optional - default: true
                        .searchIconColor(Color.WHITE) //Option - default: White
                        .setChoiceMode(MultiContactPicker.CHOICE_MODE_MULTIPLE) //Optional - default: CHOICE_MODE_MULTIPLE
                        .handleColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary)) //Optional - default: Azure Blue
                        .bubbleColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary)) //Optional - default: Azure Blue
                        .bubbleTextColor(Color.WHITE) //Optional - default: White
                        .showPickerForResult(CONTACT_PICKER_REQUEST);
            }
        });

        // Create the adapter that will return a fragment for each of the foursss
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("fab","onActivityResult" );
        if(requestCode == CONTACT_PICKER_REQUEST){
            if(resultCode == RESULT_OK) {
                Log.i("fab", "inContact_picker+request");
                List<ContactResult> results = MultiContactPicker.obtainResult(data);
                ContactsItem ci = new ContactsItem();
                for (int i = 0; i < results.size(); i++) {
                    ci.set_id(results.get(i).getContactID());
                    if(!dh.isExist(ci.get_id())){
                        ci.setName(results.get(i).getDisplayName());
                        ci.setPhone(results.get(i).getPhoneNumbers().get(0));
                        //ci.setPhoto(getByteArrayFromUri(results.get(i).getPhoto()));
                        ci.setFeat("unknown");
                        ci.setPoint(0);
                        ci.setLevel(0);
                        ci.setGroup("unknown");
                        dh.insert(ci);
                    }else{
                        ci = dh.getData(ci.get_id());
                        ci.setName(results.get(i).getDisplayName());
                        ci.setPhone(results.get(i).getPhoneNumbers().get(0));
                        //ci.setPhoto(getByteArrayFromUri(results.get(i).getPhoto()));
                        dh.update(ci);
                    }
                }
            } else if(resultCode == RESULT_CANCELED){
                System.out.println("User closed the picker without selecting items.");
            }
        }
    }
    /*public byte[] getByteArrayFromUri(Uri uri){
        try {
            Bitmap bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG,100,stream);
            byte[] data = stream.toByteArray();

            return data;
        }catch(Exception e){
            Log.e("Insert DO Photo",e.toString());
        }
        return null;
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.action_settings:
                Toast.makeText(this,"액션버튼 setting",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_profile:
                Toast.makeText(this,"액션버튼 profile",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    ContactsFragment tab1 = new ContactsFragment();
                    return tab1;
                case 1:
                    QuestFragment tab2 = new QuestFragment();
                    return tab2;
                case 2:
                    StaticsFragment tab3 = new StaticsFragment();
                    return tab3;
                case 3:
                    RankingFragment tab4 = new RankingFragment();
                    return tab4;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position){
            switch(position){
                case 0:
                    return "CONTACTS";
                case 1:
                    return "QUEST";
                case 2:
                    return "STATICS";
                case 3:
                    return "RANKING";
            }
            return null;
        }
    }
}
