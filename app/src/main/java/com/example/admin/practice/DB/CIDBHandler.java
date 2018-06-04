package com.example.admin.practice.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;
import android.util.Log;

import com.example.admin.practice.ContactsItem;

import java.util.ArrayList;
import java.util.List;

public class CIDBHandler {
    CIDBHelper dh;
    SQLiteDatabase db;
    public CIDBHandler(Context context){
        dh = new CIDBHelper(context);
    }

    public void open() throws SQLException{
        db = dh.getWritableDatabase();
    }

    public void close(){
        dh.close();
    }

    public boolean isExist(String key){
        Cursor curs = db.query(CIDBHelper.TBName, null
                , CIDBHelper.ColId + " = " + "'"+ key+"'",null,null,null,null,null);
        if(curs.getCount()== 0) return false;
        return true;
    }
    

    public long insert(ContactsItem con) {
        ContentValues cv = new ContentValues();
        cv.put(CIDBHelper.ColId, con.get_id());
        cv.put(CIDBHelper.ColName, con.getName());
        cv.put(CIDBHelper.ColPhone, con.getPhone());
        cv.put(CIDBHelper.ColPoint, con.getPoint());
        cv.put(CIDBHelper.ColLevel, con.getLevel());
        cv.put(CIDBHelper.ColFeat, con.getFeat());
        cv.put(CIDBHelper.ColGroup, con.getGroup());
        cv.put(CIDBHelper.ColBluth, con.getBluth());
        // cv.put(CIDBHelper.ColPhoto, con.getPhoto());

        long i = db.insert(CIDBHelper.TBName, null, cv);
        return i;
    }

    public void deleteGroup(String args){
        ContactsItem c;
        Cursor curs = db.query(CIDBHelper.TBName, null
                        , CIDBHelper.ColGroup + " = " + "'" + args+ "'", null, null, null, null, null);
        curs.moveToFirst();
        ContentValues cv = new ContentValues();
        cv.put(CIDBHelper.ColGroup, "unknown");
        while(!curs.isAfterLast()){
            c = cursorToContact(curs);
            db.update(CIDBHelper.TBName, cv, CIDBHelper.ColId + " = " + "'"+ c.get_id()+"'",null);
            curs.moveToNext();
        }
        curs.close();
    }

    public void update(ContactsItem con){
        ContentValues cv = new ContentValues();
        cv.put(CIDBHelper.ColId, con.get_id());
        cv.put(CIDBHelper.ColName, con.getName());
        cv.put(CIDBHelper.ColPhone, con.getPhone());
        cv.put(CIDBHelper.ColPoint, con.getPoint());
        cv.put(CIDBHelper.ColLevel, con.getLevel());
        cv.put(CIDBHelper.ColFeat, con.getFeat());
        cv.put(CIDBHelper.ColGroup, con.getGroup());
        cv.put(CIDBHelper.ColBluth, con.getBluth());
        //cv.put(CIDBHelper.ColPhoto, con.getPhoto());

        db.update(CIDBHelper.TBName, cv, CIDBHelper.ColId + " = " + "'"+ con.get_id()+"'",null);
    }

    public void delete(String i) {
        db.delete(CIDBHelper.TBName , CIDBHelper.ColId + " = " + i , null);
    }
    public String getNameByID(String cid){
        Cursor curs = db.query(CIDBHelper.TBName,null, CIDBHelper.ColId + " = " + "'"+cid+"'",null,null,null,null,null);
        curs.moveToFirst();
        return curs.getString(1);
    }
    public ContactsItem getData(String cid){
        ContactsItem c = new ContactsItem();
        Cursor curs = db.query(CIDBHelper.TBName,null, CIDBHelper.ColId + " = " + "'"+cid+"'",null,null,null,null,null);
        curs.moveToFirst();
        c.set_id(curs.getString(0));
        c.setName(curs.getString(1));
        c.setPhone(curs.getString(2));
        c.setPoint(curs.getInt(3));
        c.setLevel(curs.getInt(4));
        c.setFeat(curs.getString(5));
        c.setGroup(curs.getString(6));
        c.setBluth(curs.getString(7));
        curs.close();
        return c;
    }

    public ArrayList<ContactsItem> getPhoneListNoBluth(){
        ArrayList<ContactsItem> ret = new ArrayList<>();
        ContactsItem c;

        Cursor curs = db.query(CIDBHelper.TBName, null
                , CIDBHelper.ColBluth + " = " + "'unknown'", null, null, null, null, null);
        curs.moveToFirst();
        while(!curs.isAfterLast()){
            c = cursorToContact(curs);
            ret.add(c);
            curs.moveToNext();
        }
        curs.close();
        return ret;
    }

    public ArrayList<String> getBluth(ArrayList<String> arr){
        ArrayList<String> ret = new ArrayList<>();
        ContactsItem c;
        Log.w("bluthlist"," " + arr.size());
        for(int i=0;i<arr.size();i++) {
            Log.w("plus", "getBluth: "+arr.get(i));

            Cursor curs = db.query(CIDBHelper.TBName, null, CIDBHelper.ColBluth + " = " + "'" + arr.get(i)+"'", null, null, null, null, null);
            if(curs.getCount()== 0) continue;
            curs.moveToFirst();
            c = cursorToContact(curs);
            c.setPoint(c.getPoint()+3);
            Log.w("plus", "getBluth: "+c.getName() + " " + c.getPoint());
            this.update(c);
            ret.add(c.get_id());
            curs.close();
        }
        return ret;
    }


    public List<ContactsItem> getData(int order){//order 0 : Name asc, 1 : group asc;
        ContactsItem c;
        List<ContactsItem> con = new ArrayList<ContactsItem>();
        Cursor curs = null;
        switch (order) {
            case 0:
                curs = db.query(CIDBHelper.TBName, null
                        , null, null, null, null, CIDBHelper.ColName + " asc ", null);
                break;
            case 1:
                curs = db.query(CIDBHelper.TBName, null
                        , null, null, null, null, CIDBHelper.ColGroup + " asc ", null);
                break;
            default:
                curs = db.query(CIDBHelper.TBName, null
                        , null, null, null, null, CIDBHelper.ColName + " asc ", null);
                break;
        }
        curs.moveToFirst();
        while(!curs.isAfterLast()){
            c = cursorToContact(curs);
            con.add(c);
            curs.moveToNext();
        }
        curs.close();
        return con;
    }


    public List<ContactsItem> getGroupData(String args){//order 0 : Name asc, 1 : group asc;
        ContactsItem c;
        List<ContactsItem> con = new ArrayList<ContactsItem>();
        Cursor curs = curs = db.query(CIDBHelper.TBName, null
                , CIDBHelper.ColGroup + " = " + "'" + args+ "'", null, null, null, null, null);;

        curs.moveToFirst();
        while(!curs.isAfterLast()){
            c = cursorToContact(curs);
            con.add(c);
            curs.moveToNext();
        }
        curs.close();
        return con;
    }

    public ContactsItem cursorToContact(Cursor curs){
        ContactsItem c = new ContactsItem();

        c.set_id(curs.getString(0));
        c.setName(curs.getString(1));
        c.setPhone(curs.getString(2));
        c.setPoint(curs.getInt(3));
        c.setLevel(curs.getInt(4));
        c.setFeat(curs.getString(5));
        c.setGroup(curs.getString(6));
        c.setBluth(curs.getString(7));
        //c.setPhoto(curs.getBlob(7));
        return c;
    }

    //args : 찾을 이름, which : 0 (group)
    public int sizeofData(String args, int which){
        int ret = 0;

        switch (which){
            case 0:
                Cursor curs = db.query(CIDBHelper.TBName, null
                        , CIDBHelper.ColGroup + " = " + "'" + args+ "'",null,null,null,null,null);
                ret = curs.getCount();
                curs.close();
                break;
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;
            case 6:

                break;
            default:

                break;
        }
        return ret;
    }
}

