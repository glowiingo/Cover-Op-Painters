package ca.bcit.coveropspainters;

import java.util.Calendar;
import java.util.Date;

public class Events {
    private String eventName;
    private String userNames;
    private String email;
    private Date date;

    public Events(String eventName, String name, String email) {
        this.eventName = eventName;
        this.userNames = name;
        this.email = email;
        date = Calendar.getInstance().getTime();
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getUserNames() {
        return userNames;
    }

    public void setUserNames(String userNames) {
        this.userNames = userNames;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public String getDateCreated() {
//        return dateCreated;
//    }
//
//    public void setDateCreated(String dateCreated) {
//        this.dateCreated = dateCreated;
//    }
}
