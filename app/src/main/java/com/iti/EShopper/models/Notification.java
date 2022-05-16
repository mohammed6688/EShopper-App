package com.iti.EShopper.models;

public class Notification {
    String id;
    String notificationTitle;
    String notificationBody;
    String Date;

    public Notification() {
    }

    public Notification(String id, String notificationTitle, String notificationBody, String date) {
        this.id = id;
        this.notificationTitle = notificationTitle;
        this.notificationBody = notificationBody;
        Date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationBody() {
        return notificationBody;
    }

    public void setNotificationBody(String notificationBody) {
        this.notificationBody = notificationBody;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
