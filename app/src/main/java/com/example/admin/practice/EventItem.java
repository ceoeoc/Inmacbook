package com.example.admin.practice;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EventItem implements Parcelable {

    private String Title;
    private String StartDate;
    private String EndDate;
    private String Time;

    public EventItem(){}
    public EventItem(String title, String start_date, String end_date, String time){
        this.Title = title; this.StartDate = start_date; this.EndDate = end_date; this.Time =time;
    }

    protected EventItem(Parcel src){
        readFromParcel(src);
    }
    public void setTitle(String title){ this.Title = title;}
    public void setStartDate(String start_date){this.StartDate = start_date;}
    public void setEndDate(String end_date){this.EndDate = end_date;}
    public void setTime(String time){
        this.Time = time;
    }

    public String getTitle() {return Title;}
    public String getStartDate() { return StartDate; }
    public String getEndDate() {
        return EndDate;
    }
    public String getTime(){
        return Time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(Title);
        dest.writeString(StartDate);
        dest.writeString(EndDate);
        dest.writeString(Time);
    }

    private void readFromParcel(Parcel src ){
        Title = src.readString();
        StartDate = src.readString();
        EndDate = src.readString();
        Time = src.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public EventItem createFromParcel(Parcel in){
            return new EventItem(in);
        }

        public EventItem[] newArray(int size){
            return new EventItem[size];
        }
    };
}
