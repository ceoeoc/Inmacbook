package com.example.admin.practice;

import android.os.Parcel;
import android.os.Parcelable;

public class ContactRecord implements Parcelable {
    private String _id;
    private long lastmeet;
    private long totalmeet;
    private long total;

    public ContactRecord(){}
    public ContactRecord(String _id, long lastmeet, long totalmeet){
        this._id = _id;
        this.lastmeet = lastmeet;
        this.totalmeet = totalmeet;
        this.total = lastmeet + totalmeet;
    }

    protected ContactRecord(Parcel src){ readFromParcel(src);}
    public void set_id(String _id){this._id = _id;}
    public void setLastmeet(long lastmeet){
        this.lastmeet = lastmeet;
    }
    public void setTotalmeet(long totalmeet){
        this.totalmeet = totalmeet;
    }
    public void setTotal(long total){
        this.total = total;
    }

    public String get_id(){
        return this._id;
    }
    public long getLastmeet(){
        return this.lastmeet;
    }
    public long getTotalmeet(){
        return this.totalmeet;
    }
    public long getTotal(){
        return this.total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeLong(lastmeet);
        dest.writeLong(totalmeet);
        dest.writeLong(total);
    }

    public void readFromParcel(Parcel src){
        _id = src.readString();
        lastmeet = src.readLong();
        totalmeet = src.readLong();
        total = src.readLong();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public ContactRecord createFromParcel(Parcel in){ return new ContactRecord(in);}
        public ContactRecord[] newArray(int size){return new ContactRecord[size];}
    };
}
