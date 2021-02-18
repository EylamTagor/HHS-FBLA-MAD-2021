package com.hhsfbla.hhs_fbla_mad_2021;

import com.hhsfbla.hhs_fbla_mad_2021.classes.Business;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Notification;

public class NotificationsRVModel {

    private Notification notification;


    public void addNotification(Notification notification) {
        this.notification = notification;
    }
    private int type;
    private String hostID;
    private String userID;
    private String postID;
    private String message;

    public int getType() {
        return notification.getType();
    }
    public String getHostID() {
        return notification.getHostID();
    }
    public String getPostID() { return notification.getPostID(); }
    public String getUserID() { return notification.getUserID(); }
    public String getMessage() { return notification.getMessage(); }


    public NotificationsRVModel(Notification notification) {
        this.notification = notification;
    }
}
