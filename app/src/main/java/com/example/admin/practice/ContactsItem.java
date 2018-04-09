package com.example.admin.practice;

import android.graphics.drawable.Drawable;

public class ContactsItem {

    private Drawable icon;
    private String name;
    private String contents;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
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
