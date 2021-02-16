package com.hhsfbla.hhs_fbla_mad_2021.classes;

import android.media.Image;

import java.util.ArrayList;

public class User {
    private String name;
    private String email;
    private ArrayList<Integer> likedPosts;
    private String coverImg;
    private String pfp;
    private ArrayList<Integer> following;
    private ArrayList<Integer> followers;
    private String jobTitle;
    private String description;
    private ArrayList<String> tags;
    private ArrayList<String> skills;
    private ArrayList<Experience> experiences;
    private ArrayList<String> notifIDs;

    public User() {
        this("", "");
    }

    public User(String name, String email){
        this.name = name;
        this.email = email;
        likedPosts = new ArrayList<Integer>();
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

    public ArrayList<Integer> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(ArrayList<Integer> likedPosts) {
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

    public ArrayList<Integer> getFollowing() {
        return following;
    }

    public void setFollowing(ArrayList<Integer> following) {
        this.following = following;
    }

    public ArrayList<Integer> getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<Integer> followers) {
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

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    public ArrayList<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(ArrayList<Experience> experiences) {
        this.experiences = experiences;
    }

    public void likePost(Integer x){
        likedPosts.add(x);
    }

    public void removeLikedPost(Integer postID){
        for(int i = 0;i<likedPosts.size();i++){
            if(postID == likedPosts.get(i)){
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

}
