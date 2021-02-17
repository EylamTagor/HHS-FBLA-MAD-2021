package com.hhsfbla.hhs_fbla_mad_2021.classes;

import android.media.Image;

import java.util.ArrayList;

public class Post {

    private String title;
    private String description;
    private Image img;
    private ArrayList<String> usersLiked;
    private String hashtag;

    public Post(String title, String description, Image img, String hashtag){
        this.title = title;
        this.description = description;
        this.img = img;
        usersLiked = new ArrayList<>();
        this.hashtag = hashtag;

    }


    public Image getImg() {
        return img;
    }

    public String getHashtag() { return hashtag; }

    public void setHashtag(String hashtag) { this.hashtag = hashtag; }

    public String getTitle() {
        return title;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setImg(Image img) {
        this.img = img;
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



