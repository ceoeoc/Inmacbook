package com.example.admin.practice.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.admin.practice.DBHandler;
import com.example.admin.practice.R;


public class StaticsFragment extends Fragment {
    int num1,num2,num3,num4,num5,size;

    private DBHandler dh;
    private Spinner spinner;
    private WebView webView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.static_fragment, container, false);
        webView = (WebView) rootView.findViewById(R.id.staticwebview);
        spinner = (Spinner) rootView.findViewById(R.id.staticspinner);
        setSpinner();

        dh = new DBHandler(getActivity());
        dh.open();
        WebSettings webSettings = webView.getSettings();
        webView.addJavascriptInterface(new WebAppInterface(),"Android");
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        getStatic(0);

        return rootView;
    }
    public void setSpinner(){
        ArrayAdapter groupAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.statics , android.R.layout.simple_spinner_item);
        groupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(groupAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getStatic(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getStatic(int which){

        switch (which){
            case 0:
                num1 = dh.sizeofData("가족",0);
                num2 = dh.sizeofData("친구",0);
                num3 = dh.sizeofData("타인",0);
                num4 = dh.sizeofData("unknown",0);
                webView.loadUrl("file:///android_asset/groupchart.html");
                break;
            case 1:
                //LogsManager logsManager = new LogsManager(getActivity());
                //List<LogObject> callLogs = logsManager.getLogs(LogsManager.ALL_CALLS,);

                break;
        }


    }

    public class WebAppInterface{
        @JavascriptInterface
        public int getNum1(){
            return num1;
        }
        @JavascriptInterface
        public int getNum2(){
            return num2;
        }
        @JavascriptInterface
        public int getNum3(){
            return num3;
        }
        @JavascriptInterface
        public int getNum4(){
            return num4;
        }
        @JavascriptInterface
        public int getNum5(){
            return num5;
        }
        @JavascriptInterface
        public int getSize(){return size;}
    }

}
