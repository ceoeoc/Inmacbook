package com.example.admin.practice.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CIDBHelper extends SQLiteOpenHelper{

    public static int DBVersion = 1;

    public static String DBName = "Contacts";

    public static String TBName = "ContactTable";

    public static String ColId = "cid";
    public static String ColName = "name";
    public static String ColPhone = "phone";
    public static String ColPoint = "point";
    public static String ColLevel = "level";
    public static String ColFeat = "feat";
    public static String ColGroup = "cgroup";
    public static String ColBluth = "bluth";
    // public static String ColPhoto = "photo";

    public static String create =
            "CREATE TABLE " + TBName + " ( " + ColId + " text primary key, "
                    + ColName + " text not null, "
                    + ColPhone + " text not null, "
                    + ColPoint + " integer not null, "
                    + ColLevel + " integer not null, "
                    + ColFeat + " text not null, "
                    + ColGroup + " text not null, "
                    + ColBluth + " text);";

    public CIDBHelper(Context context){
        super(context, DBName, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TBName);
        onCreate(db);
    }
}