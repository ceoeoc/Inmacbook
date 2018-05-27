package com.example.admin.practice;

public class ListViewItem {
    private int type;

    private String titleStr;

    private ContactsItem ci;

    public void setType(int type){
        this.type = type;
    }
    public void setTitleStr(String titleStr){
        this.titleStr = titleStr;
    }
    public void setCi(ContactsItem ci){
        this.ci = ci;
    }
    public int getType(){return type;}
    public String getTitleStr(){return titleStr;}
    public ContactsItem getCi() {
        return ci;
    }
}
