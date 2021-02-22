package com.hhsfbla.hhs_fbla_mad_2021.classes;

import android.media.Image;

import java.util.ArrayList;

public class Post {

    private String title;
    private String description;
    private ArrayList<String> usersLiked;
    private String hashtag;

    public Post(){
        this("", "", "");
    }

    public Post(String title, String description, String hashtag){
        this.title = title;
        this.description = description;
        usersLiked = new ArrayList<String>();
        this.hashtag = hashtag;

    }

    public String getHashtag() { return hashtag; }

    public void setHashtag(String hashtag) { this.hashtag = hashtag; }

    public String getTitle() {
        return title;
    }

    public String getDescription() { return description; }

    public ArrayList<String> getUsersLiked() {
        return usersLiked;
    }

    public void setUsersLiked(ArrayList<String> usersLiked) {
        this.usersLiked = usersLiked;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLikes(){
        return usersLiked.size();
    }

    public void like(String userID){
        usersLiked.add(userID);
    }

    public void unlike(String userID){
        for(int i = 0; i < usersLiked.size(); i++)
            if (usersLiked.get(i).equals(userID)) {
                usersLiked.remove(i);
                break;
            }
        }
    }



