package com.example.admin.practice.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CRDBHelper extends SQLiteOpenHelper{

    public static int DBVersion = 1;

    public static String DBName = "Contacts";

    public static String TBName = "ContactRecord";

    public static String ColId = "cid";
    public static String ColLM = "LM";
    public static String ColTM = "TM";
    public static String ColTT = "TT";

    // public static String ColPhoto = "photo";

    public static String create =
            "CREATE TABLE " + TBName + " ( " + ColId + " text primary key, "
                    + ColLM + " integer not null, "
                    + ColTM + " integer not null, "
                    + ColTT + " integer not null);";

    public CRDBHelper(Context context){
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