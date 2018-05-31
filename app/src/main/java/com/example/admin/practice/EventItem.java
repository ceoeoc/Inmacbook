package com.example.admin.practice;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

public class EventItem {
    private int EventId;
    private String EventName;
    private String StDate;
    private String EndDate;
    private String Hour;
    private String Progress;
    private List<String> MemberCid;
    private int size;

    public EventItem(){
        MemberCid = new ArrayList<>();
    }
    public int getEventId() {
        return EventId;
    }

    public int getSize() {
        return MemberCid.size();
    }

    public String getEventName() {
        return EventName;
    }

    public String getEndDate() {
        return EndDate;
    }

    public String getHour() {
        return Hour;
    }

    public String getStDate() {
        return StDate;
    }

    public List<String> getMemberCid() {
        return MemberCid;
    }

    public String getProgress() {
        return Progress;
    }

    public void setEventId(int eventId) {
        EventId = eventId;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public void setHour(String hour) {
        this.Hour = hour;
    }

    public void setMemberCid(List<String> memberCid) {
        for(int i = 0 ; i < memberCid.size(); i++) {
            MemberCid.add(memberCid.get(i));
        }
    }

    public void setStDate(String stDate) {
        StDate = stDate;
    }

    public void setProgress(String progress) {
        Progress = progress;
    }

    public String getMemberCidtoJSON(){
        String ret = new String("");
        try {

            JSONArray ja = new JSONArray();
            for (int i = 0; i < MemberCid.size(); i++) {
                JSONObject jo = new JSONObject();
                jo.put("cid", MemberCid.get(i));
                ja.put(jo);
            }
            ret = ja.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    public void setMemberCidbyJSON(String json){
        try {
            JSONArray ja = new JSONArray(json);
            MemberCid.clear();
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                MemberCid.add(jo.getString("cid"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
