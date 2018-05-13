package com.example.admin.practice.activites;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
<<<<<<< HEAD
import android.content.IntentFilter;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
=======
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
>>>>>>> 1164e981a013830d39a780e74ea8194195ef7c36
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;

import com.example.admin.practice.ContactsItem;
import com.example.admin.practice.DBHandler;
<<<<<<< HEAD
import com.example.admin.practice.DB_Manager;
=======
>>>>>>> 1164e981a013830d39a780e74ea8194195ef7c36
import com.example.admin.practice.fragments.ContactsFragment;
import com.example.admin.practice.R;
import com.example.admin.practice.fragments.QuestFragment;
import com.example.admin.practice.fragments.StaticsFragment;
import com.example.admin.practice.fragments.RankingFragment;
import com.wafflecopter.multicontactpicker.ContactResult;
import com.wafflecopter.multicontactpicker.MultiContactPicker;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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


    //블루투스 변수
    private static final int REQUEST_ENABLE_BT=2;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< HEAD
        if (android.os.Build.VERSION.SDK_INT > 9) { //oncreate 에서 바로 쓰레드돌릴려고 임시방편으로 넣어둔소스

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);

        }

=======
>>>>>>> 1164e981a013830d39a780e74ea8194195ef7c36
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

        Handler handler;

        handler = new Handler(Looper.getMainLooper()) {

            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                settingGPS();

                Location userLocation = getMyLocation();

                double LAT = userLocation.getLatitude();
                double LNG = userLocation.getLongitude();
                long now =System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy//MM/dd HH:mm:ss");
                String str_datetime = sdfNow.format(date);
                TelephonyManager telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                DB_Manager db_manager;
                db_manager = new DB_Manager();
                String PhoneNum = "";
                try {
                    PhoneNum = telManager.getLine1Number();
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
                Log.d("aaa", PhoneNum + "onCreate: ");
                String str_web_id = PhoneNum;
                String str_latitude = String.valueOf(LAT);
                String str_longitude = String.valueOf(LNG);
                Log.d("aaa", LAT +"  "+ LNG + "onCreate: ");
                db_manager.information(str_web_id,str_datetime ,str_latitude, str_longitude);

                this.sendEmptyMessageDelayed(0, 30000);
            }
        };
        handler.sendEmptyMessage(0);


            //블루투스 연결위한
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(!mBluetoothAdapter.isEnabled()){
            Intent enableBtintent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtintent,REQUEST_ENABLE_BT);
        }
        ArrayList<String> bluetoothList = new ArrayList<>();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.startDiscovery();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, intentFilter);


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


        //////블루투스

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

    //GPS 좌표 받아오고 세팅 하기
    private LocationManager locationManager;
    private LocationListener locationListener;

    private Location getMyLocation() {
        Location currentLocation;
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, locationListener);

        String locationProvider = LocationManager.GPS_PROVIDER;
        currentLocation = locationManager.getLastKnownLocation(locationProvider);
        double lng = currentLocation.getLongitude();
        double lat = currentLocation.getLatitude();

        return currentLocation;
    }

    private void settingGPS() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }
    //블루투스 페어링 안된 목록 가져오기

    BluetoothAdapter mBluetoothAdapter;

    private static final int PERMISSIONS =1;

    private final BroadcastReceiver mReceiver =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)) {
                Log.d("asdasdasdas", "되냐왜나되냐뇌냐왜 되냐되라");

                BluetoothDevice device= intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.d("okokokokokokokokokokok","="+ device.getAddress());

            }
        }
    };



    protected void onStart(){
        super.onStart();
    }

    protected void onStop(){
        super.onStop();
        Log.d("멈춤멈춤멈춤", "onStop: ");
        unregisterReceiver(mReceiver);
    }

}
