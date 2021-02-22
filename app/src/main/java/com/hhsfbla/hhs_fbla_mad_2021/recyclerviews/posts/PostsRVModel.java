package com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.posts;

import android.media.Image;

import com.hhsfbla.hhs_fbla_mad_2021.classes.Business;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Experience;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Post;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;

import java.util.ArrayList;

public class PostsRVModel {

    private Post post;
    private User user;



    public void addPost(Post post) {
        this.post = post;
    }
    public void addUser(User user) {
        this.user = user;
    }


    public String getDescription() { return post.getDescription(); }
    public String getHashtag() {
        return post.getHashtag();
    }
    public String getName(){return user.getName();}
    public String getJobTitle(){return user.getJobTitle();}
    public String getPfp(){return user.getPfp();}
    public String getTitle(){return post.getTitle();}


    public PostsRVModel(Post post, User user) {
        this.post = post;
        this.user = user;
    }
}
