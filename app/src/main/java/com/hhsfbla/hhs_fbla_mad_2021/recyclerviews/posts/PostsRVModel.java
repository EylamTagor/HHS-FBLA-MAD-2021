package com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.posts;

import android.media.Image;

import com.hhsfbla.hhs_fbla_mad_2021.classes.Business;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Experience;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Post;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;

import java.util.ArrayList;

public class PostsRVModel {

    private Post post;
    private boolean isLiked;


    public void addPost(Post post) {
        this.post = post;
    }


    public long getTime(){return post.getTimePosted();}
    public String getDescription() { return post.getDescription(); }
    public String getHashtag() {
        return post.getHashtag();
    }
    public String getUserID(){
        return post.getUserPostedID();
    }
    public String getTitle(){return post.getTitle();}
    public int getLikes(){return post.getLikes();}
    public boolean isLiked(){
        return  isLiked;
    }
    public void setIsLiked(boolean b){
        isLiked = b;
    }

    public PostsRVModel(Post post) {
        this.post = post;
    }
}
