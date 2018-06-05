package com.example.admin.practice.fragments;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.admin.practice.DB.CIDBHandler;
import com.example.admin.practice.LogsManager;
import com.example.admin.practice.R;
import com.example.admin.practice.activites.MainActivity;
import com.jaredrummler.materialspinner.MaterialSpinner;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.login.LoginException;


public class StaticsFragment extends Fragment {
    int counter;
    int pos;
    public class DataSet{
        public String rowname;
        public int rowval;
        public int extra;
    }
    private ArrayList<DataSet> dataSets = new ArrayList<>();
    private CIDBHandler Cdh;
    private MaterialSpinner spinner;
    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.static_fragment, container, false);
        webView = (WebView) rootView.findViewById(R.id.staticwebview);
        spinner = (MaterialSpinner) rootView.findViewById(R.id.staticspinner);
        setSpinner();

        Cdh = new CIDBHandler(getActivity());
        Cdh.open();
        WebSettings webSettings = webView.getSettings();
        webView.addJavascriptInterface(new WebAppInterface(),"Android");
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        if(!MainActivity.groups.contains("unknown")){
            MainActivity.groups.add("unknown");
        }
        getStatic(0);

        return rootView;
    }
    public void setSpinner(){
        Resources res = getResources();
        String[] arr = res.getStringArray(R.array.statics);
        List<String> ml = new ArrayList<>();
        for(String s : arr){
            ml.add(s);
        }
        spinner.setItems(ml);
        spinner.setSelectedIndex(0);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                pos = position;
                getStatic(position);
            }
        });
    }

    public void getStatic(int which){
        HashMap<String,Integer> groups = new HashMap<>();
        for(int i = 0 ; i < MainActivity.groups.size(); i++) {
            groups.put(MainActivity.groups.get(i), 0);
        }
        HashMap<String,Integer> Extragroups = new HashMap<>();
        for(int i = 0 ; i < MainActivity.groups.size(); i++) {
            Extragroups.put(MainActivity.groups.get(i), 0);
        }
        long subTime = 1000 * 60 * 60 * 24 * -1;
        long date = System.currentTimeMillis() + 31 * subTime;
        LogsManager logsManager = new LogsManager(getActivity());
        switch (which){
            case 0:
                dataSets.clear();
                for(int i = 0 ; i < MainActivity.groups.size(); i++){
                    DataSet ds = new DataSet();
                    ds.rowname = MainActivity.groups.get(i);
                    ds.rowval = Cdh.sizeofData(MainActivity.groups.get(i),0);
                    dataSets.add(ds);
                }
                webView.loadUrl("file:///android_asset/groupchart.html");
                break;
            case 1:
                groups = logsManager.getLogs(groups,LogsManager.ALL_CALLS,date,0);
                dataSets.clear();
                for(int i = 0 ; i < MainActivity.groups.size(); i++){
                    DataSet ds = new DataSet();
                    ds.rowname = MainActivity.groups.get(i);
                    ds.rowval = groups.get(MainActivity.groups.get(i));
                    dataSets.add(ds);
                }
                webView.loadUrl("file:///android_asset/groupchart2.html");
                break;
            case 2:
                Extragroups = logsManager.getLogs(Extragroups,LogsManager.ALL_CALLS,date,1);
                dataSets.clear();
                for(int i = 0 ; i < MainActivity.groups.size(); i++){
                    DataSet ds = new DataSet();
                    ds.rowname = MainActivity.groups.get(i);
                    ds.rowval = Extragroups.get(MainActivity.groups.get(i));
                    dataSets.add(ds);
                }
                webView.loadUrl("file:///android_asset/groupchart3.html");
                break;
            case 3:
                groups = logsManager.getLogs(groups,LogsManager.ALL_CALLS,date,0);
                Extragroups = logsManager.getLogs(Extragroups,LogsManager.ALL_CALLS,date,1);
                dataSets.clear();
                for(int i = 0 ; i < MainActivity.groups.size(); i++){
                    DataSet ds = new DataSet();
                    ds.rowname = MainActivity.groups.get(i);
                    ds.rowval = groups.get(MainActivity.groups.get(i));
                    ds.extra = Extragroups.get(MainActivity.groups.get(i));
                    dataSets.add(ds);
                }
                webView.loadUrl("file:///android_asset/groupchart4.html");
                break;
            case 4:
                groups = logsManager.getLogs(groups,LogsManager.INCOMING_CALLS,date,0);
                Extragroups = logsManager.getLogs(Extragroups,LogsManager.OUTGOING_CALLS,date,0);
                dataSets.clear();
                for(int i = 0 ; i < MainActivity.groups.size(); i++){
                    DataSet ds = new DataSet();
                    ds.rowname = MainActivity.groups.get(i);
                    ds.rowval = groups.get(MainActivity.groups.get(i));
                    ds.extra = Extragroups.get(MainActivity.groups.get(i));
                    dataSets.add(ds);
                }
                webView.loadUrl("file:///android_asset/groupchart5.html");

                break;
        }
        groups.clear();
    }

    public class WebAppInterface{
        @JavascriptInterface
        public int getNum(){
            return dataSets.get(counter).rowval;
        }

        @JavascriptInterface
        public int getExtraNum(){
            return dataSets.get(counter).extra;
        }
        @JavascriptInterface
        public String getRowStr(){
            return  dataSets.get(counter).rowname;
        }
        @JavascriptInterface
        public int getSize(){return MainActivity.groups.size();}

        @JavascriptInterface
        public void setCount(){
            counter = 0;
        }
        @JavascriptInterface
        public void countPlus(){
            counter ++;
        }
    }
}
