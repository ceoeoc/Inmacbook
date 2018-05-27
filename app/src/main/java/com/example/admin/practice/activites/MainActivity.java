package com.example.admin.practice.activites;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

import android.widget.Toast;

import com.example.admin.practice.ContactRecord;
import com.example.admin.practice.ContactsItem;
import com.example.admin.practice.DB.CIDBHandler;
import com.example.admin.practice.DB.CRDBHandler;
import com.example.admin.practice.DB.WebDBManager;
import com.example.admin.practice.PermissionUtil;
import com.example.admin.practice.fragments.ContactsFragment;
import com.example.admin.practice.R;
import com.example.admin.practice.fragments.QuestFragment;
import com.example.admin.practice.fragments.StaticsFragment;
import com.example.admin.practice.fragments.RankingFragment;
import com.wafflecopter.multicontactpicker.ContactResult;
import com.wafflecopter.multicontactpicker.MultiContactPicker;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int CONTACT_PICKER_REQUEST = 991;
    public static ArrayList<String> groups;
    private static final int REQUEST_ENABLE_BT=2;

    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private CIDBHandler Cdh = null;
    private CRDBHandler Rdh = null;
    private FragmentManager frgM;
    private Fragment frg = null;
    private FloatingActionButton fab;

    public static void setStringArrayPref(Context context, String key, ArrayList<String> values){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray a = new JSONArray();
        for(int i = 0 ; i < values.size(); i++){
            a.put(values.get(i));
        }
        if(!values.isEmpty()){
            editor.putString(key, a.toString());
        }else{
            editor.putString(key,null);
        }
        editor.apply();
        }

        private ArrayList<String> getStringArrayPref(Context context, String key){
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
            String json = pref.getString(key,null);
            ArrayList<String> urls = new ArrayList<String>();
            if(json != null){
                try{
                    JSONArray a = new JSONArray(json);
                    for(int i = 0 ; i < a.length();i++){
                        String url = a.optString(i);
                        urls.add(url);
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }
        }
        return urls;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if(requestCode == 1){
            if(PermissionUtil.verifyPermission(grantResults)){

            }else{
                showRequestAgainDialog();
            }
        }else{
            super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }

    private void showRequestAgainDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("이 권한은 꼭 필요한 권한이므로, 설정에서 활성화 부탁드립니다");
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            .setData(Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
                } catch(ActivityNotFoundException e){
                    e.printStackTrace();
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                    startActivity(intent);
                }
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create();
    }

    private String getBluetoothMacAddress() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String bluetoothMacAddress = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
            try {
                Field mServiceField = bluetoothAdapter.getClass().getDeclaredField("mService");
                mServiceField.setAccessible(true);

                Object btManagerService = mServiceField.get(bluetoothAdapter);

                if (btManagerService != null) {
                    bluetoothMacAddress = (String) btManagerService.getClass().getMethod("getAddress").invoke(btManagerService);
                }
            } catch (NoSuchFieldException e) {

            } catch (NoSuchMethodException e) {

            } catch (IllegalAccessException e) {

            } catch (InvocationTargetException e) {

            }
        } else {
            bluetoothMacAddress = bluetoothAdapter.getAddress();
        }
        return bluetoothMacAddress;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(PermissionUtil.checkPermissions(this,new String[]{Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS,Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.WRITE_CALL_LOG,
                Manifest.permission.READ_CALL_LOG})){

        }else{
            PermissionUtil.requestAllPermissions(this);
        }

        if (android.os.Build.VERSION.SDK_INT > 9) { //oncreate 에서 바로 쓰레드돌릴려고 임시방편으로 넣어둔소스
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        groups = new ArrayList<String>();
        if(groups.addAll( getStringArrayPref(this,"groups"))){

        }
        Cdh = new CIDBHandler(this);
        Cdh.open();
        Rdh = new CRDBHandler(this);
        Rdh.open();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        frgM = getSupportFragmentManager();
        mSectionsPagerAdapter = new SectionsPagerAdapter(frgM);

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
                double LAT = 0;
                double LNG = 0;
                try {
                    Location userLocation = getMyLocation();

                    LAT = userLocation.getLatitude();
                    LNG = userLocation.getLongitude();
                }catch (Exception e) {
                    e.printStackTrace();
                }

                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy//MM/dd HH:mm:ss");
                String str_datetime = sdfNow.format(date);
                TelephonyManager telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                WebDBManager webDb_manager;
                webDb_manager = new WebDBManager();
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
                Log.d("aaa", LAT + "  " + LNG + "onCreate: ");
                webDb_manager.information(str_web_id, str_datetime, str_latitude, str_longitude);

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
                ContactRecord cr = new ContactRecord();
                for (int i = 0; i < results.size(); i++) {
                    ci.set_id(results.get(i).getContactID());
                    if(!Cdh.isExist(ci.get_id())){
                        cr.set_id(ci.get_id());
                        cr.setTotalcall(0);
                        cr.setTotalmeet(0);
                        cr.setTotal(0);
                        ci.setName(results.get(i).getDisplayName());
                        ci.setPhone(results.get(i).getPhoneNumbers().get(0));
                        ci.setFeat("unknown");
                        ci.setPoint(0);
                        ci.setLevel(0);
                        ci.setGroup("unknown");
                        ci.setBluth("unknown");
                        Cdh.insert(ci);
                        Rdh.insert(cr);
                    }else{
                        ci = Cdh.getData(ci.get_id());
                        ci.setName(results.get(i).getDisplayName());
                        ci.setPhone(results.get(i).getPhoneNumbers().get(0));
                        Cdh.update(ci);
                    }
                }
            } else if(resultCode == RESULT_CANCELED){
                System.out.println("User closed the picker without selecting items.");
            }
            mSectionsPagerAdapter.notifyDataSetChanged();
        }
    }

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
                Intent i = new Intent(this, PreferenceActivity.class);
                startActivity(i);
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
        public  int getItemPosition(Object object){
            return POSITION_NONE;
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
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

}
