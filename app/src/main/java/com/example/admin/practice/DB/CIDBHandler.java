package com.example.admin.practice.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;

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

    //order 0 : Name asc, 1 : group asc;
    public List<ContactsItem> getData(int order){
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

