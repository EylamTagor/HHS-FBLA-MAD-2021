package com.hhsfbla.hhs_fbla_mad_2021.classes;

import android.media.Image;

import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String email;
    private ArrayList<Post> likedPosts;
    private Image coverImg;
    private Image pfp;
    private ArrayList<User> following;
    private ArrayList<User> followers;
    private String jobTitle;
    private String description;
    private ArrayList<String> tags;
    private ArrayList<String> skills;
    private ArrayList<Experience> experiences;


    public User(int id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
        likedPosts = new ArrayList<Post>();
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

    public void likePost(Post post){
        likedPosts.add(post);
    }

    public void removeLikedPost(Post post){
        for(int i = 0;i<likedPosts.size();i++){
            if(post.getId() == likedPosts.get(i).getId()){
                likedPosts.remove(i);
                break;
            }
        }
    }
}
