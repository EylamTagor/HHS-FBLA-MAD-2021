package com.hhsfbla.hhs_fbla_mad_2021.classes;

import android.media.Image;

import java.util.ArrayList;

public class User {
    private String name;
    private String email;
    private ArrayList<String> likedPosts;
    private String coverImg;
    private String pfp;
    private ArrayList<String> following;
    private ArrayList<String> followers;
    private String jobTitle;
    private String description;
    private ArrayList<String> skills;
    private ArrayList<String> experiences;
    private ArrayList<String> notifIDs;
    private ArrayList<String> myBusinesses;
    private ArrayList<String> myPosts;

    public User() {
        this("", "");
    }

    public void setMyBusinesses(ArrayList<String> myBusinesses) {
        this.myBusinesses = myBusinesses;
    }

    public void setMyPosts(ArrayList<String> myPosts) {
        this.myPosts = myPosts;
    }

    public void setNotifIDs(ArrayList<String> notifIDs) {
        this.notifIDs = notifIDs;
    }

    public User(String name, String email){
        this.name = name;
        this.email = email;
        likedPosts = new ArrayList<String>();
        following = new ArrayList<String>();
        followers = new ArrayList<String>();
        skills = new ArrayList<String>();
        experiences = new ArrayList<String>();
        notifIDs = new ArrayList<String>();
        myBusinesses = new ArrayList<String>();
        myPosts = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(ArrayList<String> likedPosts) {
        this.likedPosts = likedPosts;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getPfp() {
        return pfp;
    }

    public void setPfp(String pfp) {
        this.pfp = pfp;
    }

    public ArrayList<String> getFollowing() {
        return following;
    }

    public void setFollowing(ArrayList<String> following) {
        this.following = following;
    }

    public ArrayList<String> getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<String> followers) {
        this.followers = followers;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    public ArrayList<String> getExperiences() {
        return experiences;
    }

    public void setExperiences(ArrayList<String> experiences) {
        this.experiences = experiences;
    }

    public void likePost(String x){
        likedPosts.add(x);
    }

    public void removeLikedPost(String postID){
        for(int i = 0;i<likedPosts.size();i++){
            if(postID.equals(likedPosts.get(i))){
                likedPosts.remove(i);
                break;
            }
        }
    }


    public void addNotification(String notifID){
        this.notifIDs.add(notifID);

    }

    public void removeNotif(String notifID){
        for(int i = 0;i<this.notifIDs.size();i++){
            if(this.notifIDs.get(i).equals(notifID)){
                this.notifIDs.remove(i);
                break;
            }
        }
    }

    public ArrayList<String> getNotifIDs(){
        return this.notifIDs;
    }

    public ArrayList<String> getMyBusinesses() {
        return myBusinesses;
    }

    public ArrayList<String> getMyPosts() {
        return myPosts;
    }

     public void addBusiness(String ID){
        this.myBusinesses.add(ID);
     }

     public void removeBusiness(String ID){
         for(int i = 0;i<this.myBusinesses.size();i++){
             if(this.myBusinesses.get(i).equals(ID)){
                 this.myBusinesses.remove(i);
                 break;
             }
         }
     }

     public void addPost(String ID){
        this.myPosts.add(ID);
     }


}
