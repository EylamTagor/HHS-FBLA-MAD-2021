package com.hhsfbla.hhs_fbla_mad_2021.classes;

import android.media.Image;

import java.util.ArrayList;

/**
 * This class represents a user
 * @author Ayush
 */
public class User {
    private String name;
    private String email;
    private ArrayList<String> likedPosts;
    private String pfp;
    private ArrayList<String> following;
    private ArrayList<String> followers;
    private String jobTitle;
    private String description;
    private String socialVision;
    private ArrayList<String> skills;
    private ArrayList<String> experiences;
    private ArrayList<String> education;
    private ArrayList<String> notifIDs;
    private ArrayList<String> myBusinesses;
    private ArrayList<String> myPosts;

    /**
     * No args constructor
     */
    public User() {
        this("", "");
    }

    /**
     * Sets a new list of businesses
     * @param myBusinesses The list of business IDs
     */
    public void setMyBusinesses(ArrayList<String> myBusinesses) {
        this.myBusinesses = myBusinesses;
    }

    /**
     * Sets a new list of posts
     * @param myPosts the new list of post IDs
     */
    public void setMyPosts(ArrayList<String> myPosts) {
        this.myPosts = myPosts;
    }

    /**
     * Sets a new list of notifications
     * @param notifIDs the new list of notification IDs
     */
    public void setNotifIDs(ArrayList<String> notifIDs) {
        this.notifIDs = notifIDs;
    }

    /**
     *
     * @param name the name of the user
     * @param email the email of the user
     */
    public User(String name, String email){
        this.name = name;
        this.email = email;
        likedPosts = new ArrayList<String>();
        following = new ArrayList<String>();
        followers = new ArrayList<String>();
        skills = new ArrayList<String>();
        experiences = new ArrayList<String>();
        notifIDs = new ArrayList<String>();
        myBusinesses = new ArrayList<String>();
        myPosts = new ArrayList<String>();
    }

    /**
     * Returns the name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a new name
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the email
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets a new email
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the list of posts
     * @return the lsit of posts
     */
    public ArrayList<String> getLikedPosts() {
        return likedPosts;
    }

    /**
     * Sets a new list of liked posts
     * @param likedPosts the new list of liked posts
     */
    public void setLikedPosts(ArrayList<String> likedPosts) {
        this.likedPosts = likedPosts;
    }

    /**
     * Returns the profile picture URI
     * @return the profile picture URI
     */
    public String getPfp() {
        return pfp;
    }

    /**
     * Sets a new profile picture URI
     * @param pfp the new picture URI
     */
    public void setPfp(String pfp) {
        this.pfp = pfp;
    }

    /**
     * Returns the list of following users
     * @return the list of following users
     */
    public ArrayList<String> getFollowing() {
        return following;
    }

    /**
     * Sets a new list of following users
     * @param following the new list of following users
     */
    public void setFollowing(ArrayList<String> following) {
        this.following = following;
    }

    /**
     * Adds a new following
     * @param ID the UID of the following
     */
    public void addFollowing(String ID){
        this.following.add(ID);
    }

    /**
     * Removes a following
     * @param ID the ID to be removed
     */
    public void removeFollowing(String ID){
        for(int i = 0;i<this.following.size();i++){
            if(this.following.get(i).equals(ID)){
                this.following.remove(i);
                break;
            }
        }
    }

    /**
     * Returns the list of followers
     * @return the list of followers
     */
    public ArrayList<String> getFollowers() {
        return followers;
    }

    /**
     * Sets a new list of followers
     * @param followers the new list of followers
     */
    public void setFollowers(ArrayList<String> followers) {
        this.followers = followers;
    }

    /**
     * Adds a follower
     * @param ID the ID of the follower
     */
    public void addFollower(String ID){
        this.followers.add(ID);
    }

    /**
     * Removes a follower
     * @param ID the ID of the follower
     */
    public void removeFollower(String ID){
        for(int i = 0;i<this.followers.size();i++){
            if(this.followers.get(i).equals(ID)){
                this.followers.remove(i);
                break;
            }
        }
    }



    /**
     * Returns the user's job title
     * @return the job title
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * Sets a new job title
     * @param jobTitle the new job title
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * Returns the user description
     * @return the user description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets a new description
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the list of the user's skills
     * @return the list of skills
     */
    public ArrayList<String> getSkills() {
        return skills;
    }

    /**
     * Sets a new list of skills
     * @param skills the new list of skills
     */
    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    /**
     * Adds a skill
     * @param skill the skill to be added
     */
    public void addSkill(String skill){
        this.skills.add(skill);
    }

    /**
     * Removes a skill
     * @param skill the skill to be removed
     */
    public void removeSkill(String skill){
        for(int i = 0;i<skills.size();i++){
            if(skills.get(i).equals(skill)){
                skills.remove(i);
                break;
            }
        }
    }

    /**
     * Returns the list of experiences
     * @return the list of experience IDs
     */
    public ArrayList<String> getExperiences() {
        return experiences;
    }

    /**
     * Sets a new list of experiences
     * @param experiences the new list of experiences
     */
    public void setExperiences(ArrayList<String> experiences) {
        this.experiences = experiences;
    }

    /**
     * Adds an experience
     * @param ID the experience ID
     */
    public void addExperiences(String ID){
        this.experiences.add(ID);
    }

    /**
     * Removes an experience
     * @param ID the experience ID
     */
    public void removeExperience(String ID){
        for(int i = 0;i<this.experiences.size();i++){
            if(this.experiences.get(i).equals(ID)){
                this.experiences.remove(i);
                break;
            }
        }
    }

    /**
     * Returns the educations the user has experienced
     * @return the list of educations
     */
    public ArrayList<String> getEducation() {
        return education;
    }

    /**
     * Sets a new list of educations
     * @param education the list of educational experiences
     */
    public void setEducation(ArrayList<String> education) {
        this.education = education;
    }

    /**
     * Adds an education
     * @param ID the education ID
     */
    public void addEducation(String ID){
        this.education.add(ID);
    }

    /**
     * Removes an education
     * @param ID the education ID
     */
    public void removeEducation(String ID){
        for(int i = 0;i<this.education.size();i++){
            if(this.education.get(i).equals(ID)){
                this.education.remove(i);
                break;
            }
        }
    }

    /**
     * Likes a new post
     * @param x the post ID
     */
    public void likePost(String x){
        likedPosts.add(x);
    }

    /**
     * Removes a liked post
     * @param postID the post that needs to be unliked
     */
    public void removeLikedPost(String postID){
        for(int i = 0;i<likedPosts.size();i++){
            if(postID.equals(likedPosts.get(i))){
                likedPosts.remove(i);
                break;
            }
        }
    }

    /**
     * Adds a notification
     * @param notifID the notification ID
     */
    public void addNotification(String notifID){
        this.notifIDs.add(notifID);

    }

    /**
     * Removes a notification
     * @param notifID the ID to be removed
     */
    public void removeNotif(String notifID){
        for(int i = 0;i<this.notifIDs.size();i++){
            if(this.notifIDs.get(i).equals(notifID)){
                this.notifIDs.remove(i);
                break;
            }
        }
    }

    /**
     * Returns the notification list
     * @return the notification list
     */
    public ArrayList<String> getNotifIDs(){
        return this.notifIDs;
    }



    /**
     * Returns the business list
     * @return the business list
     */
    public ArrayList<String> getMyBusinesses() {
        return myBusinesses;
    }

    /**
     * Returns the posts
     * @return the list of posts
     */
    public ArrayList<String> getMyPosts() {
        return myPosts;
    }

    /**
     * Returns the social vision
     * @return the social vision
     */
    public String getSocialVision() {
        return socialVision;
    }

    /**
     * Sets a new social vision
     * @param socialVision the new social vision
     */
    public void setSocialVision(String socialVision) {
        this.socialVision = socialVision;
    }

    /**
     * Adds a new business
     * @param ID the new business ID
     */
    public void addBusiness(String ID){
        this.myBusinesses.add(ID);
     }

    /**
     * Removes a business
     * @param ID the business that needs to be removed
     */
     public void removeBusiness(String ID){
         for(int i = 0;i<this.myBusinesses.size();i++){
             if(this.myBusinesses.get(i).equals(ID)){
                 this.myBusinesses.remove(i);
                 break;
             }
         }
     }

    /**
     * Adds a post
     * @param ID the post to be added
     */
     public void addPost(String ID){
        this.myPosts.add(ID);
     }

    /**
     * Removes a post
     * @param ID the post to be removed
     */
     public void removePost(String ID){
         for(int i = 0;i<this.myPosts.size();i++){
             if(this.myPosts.get(i).equals(ID)){
                 this.myPosts.remove(i);
                 break;
             }
         }
     }


}
