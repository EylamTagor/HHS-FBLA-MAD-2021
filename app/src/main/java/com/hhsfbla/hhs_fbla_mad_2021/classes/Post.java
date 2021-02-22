package com.hhsfbla.hhs_fbla_mad_2021.classes;

import android.media.Image;

import java.util.ArrayList;

/**
 * This class stores information for a post
 * @author Ayush
 */
public class Post {

    private String title;
    private String description;
    private ArrayList<String> usersLiked;
    private String hashtag;

    /**
     * No args constructor
     */
    public Post(){
        this("", "", "");
    }

    /**
     *
     * @param title The header of the post
     * @param description The description
     * @param hashtag The hashtag associated with the post
     */
    public Post(String title, String description, String hashtag){
        this.title = title;
        this.description = description;
        usersLiked = new ArrayList<String>();
        this.hashtag = hashtag;

    }

    /**
     * Returns the hashtag
     * @return the hashtag
     */
    public String getHashtag() { return hashtag; }

    /**
     * Sets the new hashtag
     * @param hashtag the new hashtag
     */
    public void setHashtag(String hashtag) { this.hashtag = hashtag; }

    /**
     * Returns the title of the post
     * @return the title of the post
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the description
     * @return the description
     */
    public String getDescription() { return description; }

    /**
     * Returns the list of users who liked the post
     * @return the list of users who liked the post
     */
    public ArrayList<String> getUsersLiked() {
        return usersLiked;
    }

    /**
     * Sets a new list of users who have liked
     * @param usersLiked the array list of users
     */
    public void setUsersLiked(ArrayList<String> usersLiked) {
        this.usersLiked = usersLiked;
    }

    /**
     * Sets a new description
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets a new title
     * @param title the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the number of likes
     * @return the number of likes
     */
    public int getLikes(){
        return usersLiked.size();
    }

    /**
     * Adds a like to the post
     * @param userID the user who liked the post
     */
    public void like(String userID){
        usersLiked.add(userID);
    }

    /**
     * Removes a like from the post
     * @param userID the user who unliked the post
     */
    public void unlike(String userID){
        for(int i = 0; i < usersLiked.size(); i++)
            if (usersLiked.get(i).equals(userID)) {
                usersLiked.remove(i);
                break;
            }
        }
    }



