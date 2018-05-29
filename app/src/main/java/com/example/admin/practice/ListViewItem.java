package com.example.admin.practice;

public class ListViewItem {
    private int type;
    private int sz;

    private String str;

    private ContactsItem ci;
    public ListViewItem(){}
    public ListViewItem(int type,String str){
        this.type = type;
        this.str = str;
    }

    public ListViewItem(int type,int sz, String Str){
        this.type = type;
        this.sz = sz;
        this.str = Str;
    }

    public ListViewItem(int type,ContactsItem ci){
        this.type = type;
        this.ci = ci;
    }

    public void setType(int type){
        this.type = type;
    }
    public void setSz(int sz){
        this.sz = sz;
    }
    public void setStr(String str){
        this.str = str;
    }
    public void setCi(ContactsItem ci){
        this.ci = ci;
    }
    public int getType(){return type;}
    public int getSz(){return sz;}
    public String getTitleStr(){return str;}
    public ContactsItem getCi() {
        return ci;
    }
}
