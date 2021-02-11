package com.hhsfbla.hhs_fbla_mad_2021.classes;

import android.media.Image;

import java.util.ArrayList;

public class Post {

    private String title;
    private String description;
    private Image img;
    private ArrayList<String> comments;
    private ArrayList<String> usersLiked;
    private ArrayList<String> hashtags;

    public Post(String title, String description, Image img, ArrayList<String> hashtags,  ArrayList<String> comments){
        this.title = title;
        this.description = description;
        this.img = img;
        usersLiked = new ArrayList<>();
        this.hashtags = hashtags;
        this.comments = comments;
    }
    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    public Image getImg() {
        return img;
    }

    public ArrayList<String> getHashtags() { return hashtags; }

    public void setHashtags(ArrayList<String> hashtags) { this.hashtags = hashtags; }

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


}
