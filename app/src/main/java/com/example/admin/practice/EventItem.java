package com.example.admin.practice;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

public class EventItem {
    public class MperP{
        String MemberCid;
        int MemP;

        public String getMemberCid() {
            return MemberCid;
        }

        public int getMemP() {
            return MemP;
        }

        public void setMemP(int memP) {
            MemP = memP;
        }

        public void setMemberCid(String memberCid) {
            MemberCid = memberCid;
        }
    }
    private int EventId;
    private String EventName;
    private String StDate;
    private String EndDate;
    private String Hour;
    private String Progress;
    private List<MperP> Members;
    private int size;

    public EventItem(){
        Members = new ArrayList<>();
    }
    public int getEventId() {
        return EventId;
    }

    public int getSize() {
        return Members.size();
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

    public List<MperP> getMembers() {
        return Members;
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

    public void setMembersbyMembers(List<MperP> members) {
        for(int i = 0 ; i < members.size(); i++) {
            Members.add(members.get(i));
        }
    }
    public void setMembers(List<String> members){
        for(int i = 0 ; i < members.size(); i++) {
            MperP mp = new MperP();
            mp.MemberCid = members.get(i);
            mp.MemP = 0;
            Members.add(mp);
        }
    }

    public void setStDate(String stDate) {
        StDate = stDate;
    }

    public void setProgress(String progress) {
        Progress = progress;
    }

    public String getMemberbyJSON(){
        String ret = new String("");
        try {

            JSONArray ja = new JSONArray();
            for (int i = 0; i < Members.size(); i++) {
                JSONObject jo = new JSONObject();
                jo.put("cid", Members.get(i).MemberCid);
                jo.put("prg",Members.get(i).MemP);
                ja.put(jo);
            }
            ret = ja.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    public void setMemberbyJSON(String json){
        try {
            JSONArray ja = new JSONArray(json);
            Members.clear();
            for (int i = 0; i < ja.length(); i++) {
                MperP mp = new MperP();
                JSONObject jo = ja.getJSONObject(i);
                mp.MemberCid = jo.getString("cid");
                mp.MemP = jo.getInt("prg");
                Members.add(mp);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
