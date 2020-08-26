package com.example.finalprojectgroup8;

public class My_Appointments_Helper {
    String appointerid,date,time;

    public My_Appointments_Helper(String appointerid, String date, String time) {
        this.appointerid = appointerid;
        this.date = date;
        this.time = time;
    }

    public String getAppointerid() {
        return appointerid;
    }

    public void setAppointerid(String appointerid) {
        this.appointerid = appointerid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
