package com.hhsfbla.hhs_fbla_mad_2021.classes;

public class Notification {

    //1 = someone liked your post
    //2 = someone followed you
    private int type;
    private String hostID;
    private String userID;
    private String postID;
    private String message;

    public Notification(String hostID, String userID){
        this.type = 2;
        this.hostID = hostID;
        this.userID = userID;
    }

    public Notification(String hostID, String userID, String postID){
        this.type = 1;
        this.hostID = hostID;
        this.userID = userID;
        this.postID = postID;
    }
}
