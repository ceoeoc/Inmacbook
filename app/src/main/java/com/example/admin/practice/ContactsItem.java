package com.example.admin.practice;

import android.graphics.drawable.Drawable;

public class ContactsItem {

    private Drawable icon;
    private int _Id;
    private String name;
    private String phone;
    private int point;
    private int level;
    private String feat;
    private String group;

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public int get_Id(){
        return _Id;
    }

    public void set_Id(int _Id){
        this._Id = _Id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String contents) {
        this.phone = contents;
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
