package com.example.helphomeless;

public class AVRList {
    int id;
    String status;

    public AVRList(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
