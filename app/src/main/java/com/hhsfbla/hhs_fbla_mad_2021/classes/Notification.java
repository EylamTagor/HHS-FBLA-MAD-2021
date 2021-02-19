package com.hhsfbla.hhs_fbla_mad_2021.classes;

public class Notification {

    //1 = someone liked your post
    //2 = someone followed you
    private int type;
    private String hostID;
    private String userID;
    private String postID;

    //Follow
    public Notification(String hostID, String userID){
        this.type = 2;
        this.hostID = hostID;
        this.userID = userID;

        //TEMP
        //this.message = "Joseph narglesmen followed you";
        // Define message using "user name followed you"

    }

    //Like
    public Notification(String hostID, String userID, String postID){
        this.type = 1;
        this.hostID = hostID;
        this.userID = userID;
        this.postID = postID;

        //TEMP
        //this.message= "John doe liked your post";
        // Define message using "user name liked your post"
    }
    public String getHostID() {
        return hostID;
    }

    public void setHostID(String hostID) {
        this.hostID = hostID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
