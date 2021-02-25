package com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.my_businesses;

import com.hhsfbla.hhs_fbla_mad_2021.classes.Business;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Education;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Experience;

public class MyBusinessesRVModel {

    private Business business;

    public String getLogo() {
        return business.getLogo();
    }
    public String getName() {
        return business.getName();
    }
    public String getWebsite() {
        return business.getWebsite();
    }
    public boolean hasLogo(){
        if(business.getLogo().equals("") || business.getLogo() == null ){
            return false;
        }
        else {
            return true;
        }
    }


    public MyBusinessesRVModel(Business business) {
        this.business = business;
    }
}
