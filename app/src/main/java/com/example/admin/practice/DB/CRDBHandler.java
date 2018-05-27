package com.example.admin.practice.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.admin.practice.ContactRecord;

import java.util.ArrayList;
import java.util.List;

public class CRDBHandler {
    CRDBHelper dh;
    SQLiteDatabase db;
    public CRDBHandler(Context context){ dh = new CRDBHelper(context);}

    public void open() throws SQLException{
        db = dh.getWritableDatabase();
    }
    public void close(){
        dh.close();
    }

    public boolean isExist(String key){
        Cursor curs = db.query(CRDBHelper.TBName, null,
                CRDBHelper.ColId + " = " + "'" + key + "'", null, null,null,null,null);
        if(curs.getCount() == 0) return false;
        return true;
    }

    public long insert(ContactRecord con){
        ContentValues cv = new ContentValues();
        cv.put(CRDBHelper.ColId, con.get_id());
        cv.put(CRDBHelper.ColLM, con.getLastmeet());
        cv.put(CRDBHelper.ColLC, con.getLastcall());
        cv.put(CRDBHelper.ColTM, con.getTotalmeet());
        cv.put(CRDBHelper.ColTC, con.getTotalcall());
        cv.put(CRDBHelper.ColTT, con.getTotal());

        long i = db.insert(CRDBHelper.TBName,null, cv);
        return i;
    }

    public void update(ContactRecord con){
        ContentValues cv = new ContentValues();
        cv.put(CRDBHelper.ColId, con.get_id());
        cv.put(CRDBHelper.ColLM, con.getLastmeet());
        cv.put(CRDBHelper.ColLC, con.getLastcall());
        cv.put(CRDBHelper.ColTM, con.getTotalmeet());
        cv.put(CRDBHelper.ColTC, con.getTotalcall());
        cv.put(CRDBHelper.ColTT, con.getTotal());

        db.update(CRDBHelper.TBName,cv, CRDBHelper.ColId + " = " + "'" + con.get_id() + "'", null);
    }

    public void delete(String i){
        db.delete(CRDBHelper.TBName, CRDBHelper.ColId + " = " + i,null);
    }

    public ContactRecord getData(String cid){
        ContactRecord c = new ContactRecord();
        Cursor curs = db.query(CRDBHelper.TBName,null, CRDBHelper.ColId + " = " + "'"+cid+"'",null,null,null,null,null);
        curs.moveToFirst();
        c.set_id(curs.getString(0));
        c.setLastmeet(curs.getLong(1));
        c.setLastcall(curs.getLong(2));
        c.setTotalmeet(curs.getLong(3));
        c.setTotalcall(curs.getLong(4));
        c.setTotal(curs.getLong(5));
        curs.close();
        return c;
    }
    public List<ContactRecord> getData(){
        ContactRecord c;
        List<ContactRecord> con = new ArrayList<>();

        Cursor curs = db.query(CRDBHelper.TBName, null
                ,null,null,null,null, null,null);

        curs.moveToFirst();
        while(!curs.isAfterLast()){
            c = cursorToRecord(curs);
            con.add(c);
            curs.moveToNext();
        }
        curs.close();
        return con;
    }

    public ContactRecord cursorToRecord(Cursor curs){
        ContactRecord c = new ContactRecord();

        c.set_id(curs.getString(0));
        c.setLastmeet(curs.getLong(1));
        c.setLastcall(curs.getLong(2));
        c.setTotalmeet(curs.getLong(3));
        c.setTotalcall(curs.getLong(4));
        c.setTotal(curs.getLong(5));
        return c;
    }
}
