package com.example.finalprojectgroup8;

public class RecyclerViewList {
    String username;
    String location;
    String rate;
    String service;
    public RecyclerViewList() {
    }

    public RecyclerViewList(String username, String location,String rate,String service) {
        this.username = username;
        this.location = location;
        this.rate = rate;
        this.service=service;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String email) {
        this.location = location;
    }
}
