package com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.my_businesses;

import com.hhsfbla.hhs_fbla_mad_2021.classes.Business;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Education;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Experience;

public class MyBusinessesRVModel {

    private Business business;
    private String id;

    /**
     * Returns the logo
     * @return the logo
     */
    public String getLogo() {
        return business.getLogo();
    }

    /**
     * Returns the name
     * @return the name
     */
    public String getName() {
        return business.getName();
    }

    /**
     * Returns the website link
     * @return the website link
     */
    public String getWebsite() {
        return business.getWebsite();
    }

    /**
     * Returns the firebase ID
     * @return the firebase ID
     */
    public String getId() {
        return id;
    }

    /**
     * Returns whether the business has a logo
     * @return whether the business has a log
     */
    public boolean hasLogo(){
        if(business.getLogo().equals("") || business.getLogo() == null ){
            return false;
        }
        else {
            return true;
        }
    }

    /**
     *
     * @param business the business to construct the model around
     * @param id the firebase id
     */
    public MyBusinessesRVModel(Business business, String id) {
        this.business = business;
        this.id = id;
    }
}
