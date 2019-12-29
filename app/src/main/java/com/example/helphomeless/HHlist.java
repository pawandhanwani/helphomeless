package com.example.helphomeless;

public class HHlist {
    private String photoloc;
    private String address;
    private String date;
    private String name;
    private String status;

    public String getPhotoloc() {
        return photoloc;
    }

    public String getAddress() {
        return address;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public HHlist(String photoloc, String address, String date, String name, String status) {
        this.photoloc = photoloc;
        this.address = address;
        this.date = date;
        this.name = name;
        this.status = status;
    }
}
