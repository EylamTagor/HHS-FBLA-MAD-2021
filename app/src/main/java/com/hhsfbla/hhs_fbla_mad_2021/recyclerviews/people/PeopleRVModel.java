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


    public String getName() {
        if(isUser)
            return user.getName();
        return business.getName();
    }

    public String getHeader() {
        if(isUser)
            return user.getJobTitle();
        return business.getWebsite();
    }

    public String getPfp() {
        if(isUser)
            return user.getPfp();
        return business.getLogo();
    }

    public String getId() {
        return id;
    }

    public boolean isUser() {
        return isUser;
    }

    public PeopleRVModel(User user, String id) {
        isUser = true;
        this.user = user;
        this.id = id;
    }

}
