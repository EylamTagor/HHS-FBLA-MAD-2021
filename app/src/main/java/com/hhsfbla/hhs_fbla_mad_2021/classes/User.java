package com.hhsfbla.hhs_fbla_mad_2021.classes;

import android.media.Image;

import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String email;
    private ArrayList<Integer> likedPosts;
    private Image coverImg;
    private Image pfp;
    private ArrayList<Integer> following;
    private ArrayList<Integer> followers;
    private String jobTitle;
    private String description;
    private ArrayList<String> tags;
    private ArrayList<String> skills;
    private ArrayList<Experience> experiences;


    public User(int id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
        likedPosts = new ArrayList<Integer>();
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
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

    public Image getPfp() {
        return pfp;
    }

    public void setPfp(Image pfp) {
        this.pfp = pfp;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
