package com.example.admin.practice.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;
import android.util.Log;

import com.example.admin.practice.EventItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class EVDBHandler {
    EVDBHelper dh;
    SQLiteDatabase db;

    public EVDBHandler(Context context){
        dh = new EVDBHelper(context);
    }

    public void open() throws SQLException {
        db = dh.getWritableDatabase();
    }
    public void close(){
        dh.close();
    }

    public long insert(EventItem con) {
        ContentValues cv = new ContentValues();
        cv.put(EVDBHelper.ColEid, con.getEventId());
        cv.put(EVDBHelper.ColName, con.getEventName());
        cv.put(EVDBHelper.ColStDate, con.getStDate());
        cv.put(EVDBHelper.ColEdDate, con.getEndDate());
        cv.put(EVDBHelper.ColHour, con.getHour());
        cv.put(EVDBHelper.ColPrg, con.getProgress());
        cv.put(EVDBHelper.ColMem, con.getMemberbyJSON());

        long i = db.insert(EVDBHelper.TBName, null, cv);
        return i;
    }


    public void update(EventItem con){
        ContentValues cv = new ContentValues();
        cv.put(EVDBHelper.ColEid, con.getEventId());
        cv.put(EVDBHelper.ColName, con.getEventName());
        cv.put(EVDBHelper.ColStDate, con.getStDate());
        cv.put(EVDBHelper.ColEdDate, con.getEndDate());
        cv.put(EVDBHelper.ColHour, con.getHour());
        cv.put(EVDBHelper.ColPrg, con.getProgress());
        cv.put(EVDBHelper.ColMem, con.getMemberbyJSON());

        db.update(EVDBHelper.TBName, cv, EVDBHelper.ColEid + " = " + "'"+ con.getEventId()+"'",null);
    }

    public void delete(String i) {
        db.delete(EVDBHelper.TBName , EVDBHelper.ColEid + " = " + i , null);
    }

    public EventItem getData(int cid){
        EventItem c = new EventItem();
        Cursor curs = db.query(EVDBHelper.TBName,null, EVDBHelper.ColEid + " = " + "'"+cid+"'",null,null,null,null,null);
        curs.moveToFirst();
        c.setEventId(curs.getInt(0));
        c.setEventName(curs.getString(1));
        c.setStDate(curs.getString(2));
        c.setEndDate(curs.getString(3));
        c.setHour(curs.getString(4));
        c.setProgress(curs.getString(5));
        c.setMemberbyJSON(curs.getString(6));
        curs.close();
        return c;
    }

    public List<EventItem> getData(){//order 0 : Name asc, 1 : group asc;
        EventItem c;
        List<EventItem> con = new ArrayList<EventItem>();
        Cursor curs = null;
        curs = db.query(EVDBHelper.TBName, null
                , null, null, null, null, EVDBHelper.ColEdDate + " asc ", null);
        curs.moveToFirst();
        while(!curs.isAfterLast()){
            c = cursorToEvent(curs);
            con.add(c);
            curs.moveToNext();
        }
        curs.close();
        return con;
    }

    public void getBluth(ArrayList<String> lists,String now){
        EventItem c;
        Cursor curs = db.query(EVDBHelper.TBName, null
                , EVDBHelper.ColStDate + " >= " + "'"+now + "' and " + EVDBHelper.ColEdDate + " <= " + "'" + now+ "'" , null, null, null, null, null);
        curs.moveToFirst();
        while(!curs.isAfterLast()){
            c = cursorToEvent(curs);
            curs.moveToNext();
            Log.d("between event", "STD: " + c.getStDate() + "EDD: " + c.getEndDate());
            for(int i = 0 ; i < lists.size(); i++){
                for(EventItem.MperP s : c.getMembers()){
                    if(s.getMemberCid().equals(lists.get(i))){
                        s.setMemP(s.getMemP() + 3);
                    }
                }

            }
        }

    }
    public EventItem cursorToEvent(Cursor curs){
        EventItem c = new EventItem();

        c.setEventId(curs.getInt(0));
        c.setEventName(curs.getString(1));
        c.setStDate(curs.getString(2));
        c.setEndDate(curs.getString(3));
        c.setHour(curs.getString(4));
        c.setProgress(curs.getString(5));
        c.setMemberbyJSON(curs.getString(6));

        return c;
    }
}
