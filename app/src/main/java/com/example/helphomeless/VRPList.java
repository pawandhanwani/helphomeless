package com.example.helphomeless;

public class VRPList {
    int rid;
    int sid;
    String name;
    String status;

    public int getSid() {
        return sid;
    }

    public VRPList(int rid, int sid, String name, String status) {
        this.rid = rid;
        this.sid = sid;
        this.name = name;
        this.status = status;
    }

    public int getRid() {
        return rid;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }
}
