package com.example.helphomeless;

public class SAList {
    private int sid;
    private int uid;
    private int aid;
    private String name;
    private String aname;
    private String photoloc;
    private String address;



    public SAList(int sid, int uid, int aid, String name, String aname, String photoloc, String address) {
        this.sid = sid;
        this.uid = uid;
        this.aid = aid;
        this.name = name;
        this.aname = aname;
        this.photoloc = photoloc;
        this.address = address;
    }

    public int getSid() {
        return sid;
    }

    public String getName() {
        return name;
    }

    public String getPhotoloc() {
        return photoloc;
    }

    public String getAddress() {
        return address;
    }

    public int getUid() {
        return uid;
    }

    public int getAid() {
        return aid;
    }

    public String getAname() {
        return aname;
    }
}
