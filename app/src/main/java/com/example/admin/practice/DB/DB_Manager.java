package com.example.admin.practice.DB;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * Created by Tuna on 2018-05-07.
 */

public class DB_Manager {
    private String urlPath;
    private final String information_urlPath ="http://13.125.52.147/information.php";
    private String str_web_id;
    private String str_latitude;
    private String str_longitude;
    private String str_datetime;
    private ArrayList results;

    public ArrayList<String> information(String str_web_id,String str_datetime ,String str_latitude, String str_longitude){
        urlPath = information_urlPath;
        this.str_web_id = str_web_id;
        this.str_datetime = str_datetime;
        this.str_latitude = str_latitude;
        this.str_longitude = str_longitude;

        try{
            results = new Postidgps().execute().get();
        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e){
            e.printStackTrace();
        }
        return results;
    }

    class Postidgps extends AsyncTask<Void, Void, ArrayList<String>>{
        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            try{
                URL url = new URL(urlPath);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setUseCaches(false);
                con.setRequestMethod("POST");

                String param = "str_web_id="+str_web_id+"&str_datetime="+str_datetime+"&str_latitude="+str_latitude+"&str_longitude="+str_longitude;
                OutputStream outputStream = con.getOutputStream();
                outputStream.write(param.getBytes());
                outputStream.flush();
                outputStream.close();
                Log.d("aaa", str_longitude+"doInBackground: ");
                BufferedReader rd = null;
                ArrayList<String> qResults = new ArrayList<>();
                rd = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
                String line = "";
                while((line = rd.readLine())!=null){
                    Log.d("BufferedReader: ", line);
                    if(line != null){
                        qResults.add(line);
                    }
                }
                return qResults;
            } catch (UnsupportedEncodingException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(ArrayList<String> qResults){
            super.onPostExecute(qResults);
        }
    }
}
