package com.hhsfbla.hhs_fbla_mad_2021.classes;

/**
 * This class represents a notification
 */
public class Notification {

    //1 = someone liked your post
    //2 = someone followed you
    private int type;
    private String hostID;
    private String userID;
    private String postID;

    /**
     * No args constructor
     */
    public Notification(){
        this("", "", "");
    }

    /**
     * The follow constructor
     * @param hostID The firebase ID of the user receiving the notification
     * @param userID The firebase ID of the user involved in the notification
     */
    //Follow
    public Notification(String hostID, String userID){
        this.type = 2;
        this.hostID = hostID;
        this.userID = userID;

        //TEMP
        //this.message = "Joseph narglesmen followed you";
        // Define message using "user name followed you"

    }

    /**
     * The like constructor
     * @param hostID The firebase ID of the user receiving the notification
     * @param userID The firebase ID of the user involved in the notification
     * @param postID The firebase ID of the post liked
     */
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

    /**
     * Returns the host ID
     * @return the host ID
     */
    public String getHostID() {
        return hostID;
    }

    /**
     * Sets the host ID
     * @param hostID the new host ID
     */
    public void setHostID(String hostID) {
        this.hostID = hostID;
    }

    /**
     * Returns the user ID
     * @return the user ID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets the new user ID
     * @param userID the new user ID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * Returns the post ID
     * @return the post ID
     */
    public String getPostID() {
        return postID;
    }

    /**
     * Sets the new post ID
     * @param postID the new post ID
     */
    public void setPostID(String postID) {
        this.postID = postID;
    }

    /**
     * Returns the type of notification 1 = a like, 2 = a follow
     * @return the type of notification
     */
    public int getType() {
        return type;
    }

    /**
     * Sets the type of notification
     * @param type the new type of notification
     */
    public void setType(int type) {
        this.type = type;
    }

}
