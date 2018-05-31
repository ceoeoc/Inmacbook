package com.example.admin.practice;

import android.os.Parcel;
import android.os.Parcelable;

public class ContactsItem implements Parcelable {

    private String _id;
    private String name;
    private String phone;
    private int point;
    private int level;
    private String feat;
    private String group;
    private String bluth;
    //private byte[] photo;
    public ContactsItem(){}
    public ContactsItem(String _id, String name, String phone, int point, int level, String feat, String group,String bluth){
        this._id = _id; this.name = name; this.phone = phone; this.point = point; this.level = level; this.feat = feat; this.group = group;this.bluth = bluth;
    }

    protected ContactsItem(Parcel src){
        readFromParcel(src);
    }
    public void set_id(String _id){ this._id = _id;}
    public void setName(String name){this.name = name;}
    public void setPhone(String phone){this.phone = phone;}
    public void setPoint(int point){
        this.point = point;
        while(point >= 100){
            point -= 100;
            level++;
        }
    }
    public void setLevel(int level){
        this.level = level;
    }
    public void setFeat(String feat){
        this.feat = feat;
    }
    public void setGroup(String group){
        this.group = group;
    }
    public void setBluth(String bluth){this.bluth = bluth;}
    //public void setPhoto(byte[] photo){this.photo = photo;}

    public String get_id() {
        return _id;
    }
    public String getName() { return name; }
    public String getPhone() {
        return phone;
    }
    public int getPoint(){
        return point;
    }
    public int getLevel(){
        return level;
    }
    public String getFeat(){
        return feat;
    }
    public String getGroup(){ return group;  }
    public String getBluth(){return bluth; }
    //public byte[] getPhoto() { return photo;  }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(_id);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeInt(point);
        dest.writeInt(level);
        dest.writeString(feat);
        dest.writeString(group);
        dest.writeString(bluth);
    }

    private void readFromParcel(Parcel src ){
        _id = src.readString();
        name = src.readString();
        phone = src.readString();
        point = src.readInt();
        level = src.readInt();
        feat = src.readString();
        group = src.readString();
        bluth = src.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public ContactsItem createFromParcel(Parcel in){
            return new ContactsItem(in);
        }

        public ContactsItem[] newArray(int size){
            return new ContactsItem[size];
        }
    };
}
