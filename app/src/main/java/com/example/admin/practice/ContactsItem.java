package com.example.admin.practice;

import android.graphics.drawable.Drawable;

import com.wafflecopter.multicontactpicker.ContactResult;

public class ContactsItem{

    private String
    private int point;
    private int level;
    private String feat;
    private String group;

    public void setCr(ContactResult cr){
        this.cr = cr;
    }

    public ContactResult getCr(){
        return cr;
    }

    public int getPoint(){
        return point;
    }

    public void setPoint(int point){
        this.point = point;
    }

    public int getLevel(){
        return level;
    }

    public void setLevel(int level){
        this.level = level;
    }

    public String getFeat(){
        return feat;
    }

    public void setFeat(String feat){
        this.feat = feat;
    }

    public String getGroup(){
        return group;
    }

    public void setGroup(){
        this.group = group;
    }
}
