package ca.bcit.coveropspainters;

import java.util.Calendar;
import java.util.Date;

public class Events {
    private String eventName;
    private String createdBy;
    private String email;
    private String address;
    private String time;
    private String date;

    public Events(String eventName, String name, String email, String address, String time, String date) {
        this.eventName = eventName;
        this.createdBy = name;
        this.email = email;
        this.address = address;
        this.time = time;
        this.date = date;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


//    public String getDateCreated() {
//        return dateCreated;
//    }
//
//    public void setDateCreated(String dateCreated) {
//        this.dateCreated = dateCreated;
//    }
}
