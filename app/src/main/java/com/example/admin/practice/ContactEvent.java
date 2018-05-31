package com.example.admin.practice;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ContactEvent implements Parcelable {
    private String _title="title";
    private String _start="start date";
    private String _end="end date";
    private int _time=0;

    public ContactEvent(){}
    public ContactEvent(String _title, String _start, String _end, int _time){
        this._title = _title; this._start = _start; this._end = _end; this._time = _time;
    }

    protected ContactEvent(Parcel src){
        readFromParcel(src);
    }
    public void set_title(String _title){ this._title = _title;}
    public void set_start(String _start){ this._start = _start;}
    public void set_end(String _end){ this._end = _end;}
    public void set_time(int _time){ this._time = _time; }

    public String get_title() {
        return _title;
    }
    public String get_start() { return _start; }
    public String get_end() { return _end; }
    public int get_time(){
        return _time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(_title);
        dest.writeString(_start);
        dest.writeString(_end);
        dest.writeInt(_time);
    }

    private void readFromParcel(Parcel src ){
        _title = src.readString();
        _start = src.readString();
        _end = src.readString();
        _time = src.readInt();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public ContactEvent createFromParcel(Parcel in){
            return new ContactEvent(in);
        }

        public ContactEvent[] newArray(int size){
            return new ContactEvent[size];
        }
    };
}
