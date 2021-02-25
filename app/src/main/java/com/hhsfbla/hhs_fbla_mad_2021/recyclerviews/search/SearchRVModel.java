package com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.search;

import com.hhsfbla.hhs_fbla_mad_2021.classes.Business;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Experience;
import com.hhsfbla.hhs_fbla_mad_2021.classes.JobOffer;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;

public class SearchRVModel {

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
            return user.getName();
        return "Business";
    }

    public String getPfp() {
        if(isUser)
            return user.getPfp();
        return business.getLogo();
    }

    private String getId() {
        return id;
    }

    public SearchRVModel(User user, String id) {
        isUser = true;
        this.user = user;
        this.id = id;
    }

    public SearchRVModel(Business business, String id) {
        isUser = false;
        this.business = business;
        this.id = id;
    }
}
