package com.example.admin.practice.DB;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


/**
 * Created by Tuna on 2018-05-07.
 */

public class WebDBManager {
    private String urlPath;
    private String furlPath;
    private final String information_urlPath ="http://13.125.52.147/information.php";
    private final String finformation_urlPath="http://13.125.52.147/finformation.php";
    private String str_web_id;
    private String str_latitude;
    private String str_longitude;
    private String str_datetime;
    private String bltaddr;
    private ArrayList results;
    private String fresults;

    private String str_friend_web_id;
    private String str_friend_bltaddr;

    TextView textView;
    Getbltaddr getbltaddr;

    public String finformation(String str_friend_web_id){
        furlPath = finformation_urlPath;
        this.str_friend_web_id=str_friend_web_id;
        this.str_friend_bltaddr=str_friend_bltaddr;
        try {
            fresults = new Getbltaddr().execute().get();
        } catch (ExecutionException e){
            e.printStackTrace();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        return fresults;

    }

    class Getbltaddr extends AsyncTask<Void, Void, String>{
        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(furlPath);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setUseCaches(false);
                con.setRequestMethod("POST");

                String param = "str_friend_web_id="+str_friend_web_id;//+"&str_datetime="+str_datetime+"&str_latitude="+str_latitude+"&str_longitude="+str_longitude+"&bltaddr="+bltaddr;
                OutputStream outputStream = con.getOutputStream();
                outputStream.write(param.getBytes());
                outputStream.flush();
                outputStream.close();

                BufferedReader rd = null;
                //ArrayList<String> qResults = new ArrayList<>();
                String qResults="";
                rd = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
                String line = "";
                while((line = rd.readLine())!=null){
                    Log.d("BufferedReader: ", line);
                    if(line != null){
                        qResults += line;
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
        //protected void onPostExecute(ArrayList<String> qResults){
        //    super.onPostExecute(qResults);
        //}
    }

    /*class Getbltaddr extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... arg0){
            try{
                String str_friend_web_id = arg0[0];

                String link = "http://13.125.52.147/finformation.php?pn"+str_friend_web_id;
                URL url = new URL(link);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuffer sb = new StringBuffer("");
                String line="";

                while((line = in.readLine())!=null){
                    sb.append(line);
                    break;
                }
                in.close();
                return sb.toString();

            } catch (UnsupportedEncodingException e){
                e.printStackTrace();
                return new String("Exception: " + e.getMessage());
            } catch (IOException e){
                e.printStackTrace();
                return new String("Exception: " + e.getMessage());
            } catch (URISyntaxException e){
                e.printStackTrace();
                return new String("Exception: " + e.getMessage());
            }
        }
    }*/

    public ArrayList<String> information(String str_web_id,String str_datetime ,String str_latitude, String str_longitude,String bltaddr){
        urlPath = information_urlPath;
        this.str_web_id = str_web_id;
        this.str_datetime = str_datetime;
        this.str_latitude = str_latitude;
        this.str_longitude = str_longitude;
        this.bltaddr = bltaddr;


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

                String param = "str_web_id="+str_web_id+"&str_datetime="+str_datetime+"&str_latitude="+str_latitude+"&str_longitude="+str_longitude+"&bltaddr="+bltaddr;
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