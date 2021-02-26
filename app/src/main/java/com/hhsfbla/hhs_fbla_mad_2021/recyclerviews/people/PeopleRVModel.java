package com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.people;

import com.hhsfbla.hhs_fbla_mad_2021.classes.Business;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Experience;
import com.hhsfbla.hhs_fbla_mad_2021.classes.JobOffer;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;

public class PeopleRVModel {

    private String id;
    private User user;
    private Business business;
    private boolean isUser;

    /**
     * Returns the name
     * @return the name
     */
    public String getName() {
        if(isUser)
            return user.getName();
        return business.getName();
    }

    /**
     * Returns the header
     * @return the header
     */
    public String getHeader() {
        if(isUser)
            return user.getJobTitle();
        return business.getWebsite();
    }

    /**
     * Returns the profile picture
     * @return the profile picture
     */
    public String getPfp() {
        if(isUser)
            return user.getPfp();
        return business.getLogo();
    }

    /**
     * Returns the firebase ID
     * @return the firebase ID
     */
    public String getId() {
        return id;
    }

    /**
     * Returns whether the person is a user
     * @return whether the person is a user
     */
    public boolean isUser() {
        return isUser;
    }

    /**
     *
     * @param user The user to construct the model around
     * @param id the firebase ID
     */
    public PeopleRVModel(User user, String id) {
        isUser = true;
        this.user = user;
        this.id = id;
    }

}
