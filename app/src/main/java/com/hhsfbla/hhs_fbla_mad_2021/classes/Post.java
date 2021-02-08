package com.hhsfbla.hhs_fbla_mad_2021.classes;

import android.media.Image;

import java.util.ArrayList;

public class Post {

    private int id;
    private String title;
    private String description;
    private Image img;
    private ArrayList<User> usersLiked;
    private ArrayList<String> hashtags;

    public Post(int id, String title, String description, Image img, ArrayList<String> hashtags){
        this.id = id;
        this.title = title;
        this.description = description;
        this.img = img;
        usersLiked = new ArrayList<User>();
        this.hashtags = hashtags;
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

    public int getId() {
        return id;
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

    public void like(User user){
        usersLiked.add(user);
    }

    public void unlike(User user){
        for(int i = 0;i<usersLiked.size();i++){
            if(user.getId() == usersLiked.get(i).getId()){
                usersLiked.remove(i);
                break;
            }
        }
    }


}
