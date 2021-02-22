package com.hhsfbla.hhs_fbla_mad_2021.classes;

import android.widget.ImageView;

import java.util.ArrayList;

//Temp stuff here so I can do profile page stuff

/**
 *
 * Represents a business
 * @author Ayush
 */
public class Business {
    private String name;
    private String logo;
    private ArrayList<String> jobOffers;

    /**
     * No args constructor for the Business class
     */
    public Business(){
        name = "";
        logo = "";
    }

    /**
     *
     * @param name the name of the business
     * @param logo the URI of the logo image
     */
    public Business(String name, String logo){
        this.name = name;
        this.logo = logo;
        jobOffers = new ArrayList<String>();
    }

    /**
     * Returns the name of the business
     * @return the name of the business
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the business
     * @param name the new name of the business
     */
    public void setTitle(String name) {
        this.name = name;
    }

    /**
     * Returns the logo URI
     * @return the logo URI
     */
    public String getLogo() {
        return logo;
    }

    /**
     * Sets the new URI of the logo
     * @param logo the new URI of the logo
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * Adds a new job that the business is offering
     * @param JOID the FirebaseID of the job offer object
     */
    public void addJobOffer(String JOID){
        jobOffers.add(JOID);
    }

    /**
     * Returns the array list of job offers
     * @return the array list of job offers
     */

    public ArrayList<String> getJobOffers() {
        return jobOffers;
    }

    /**
     * Removes a job offer
     * @param offerID the ID of the offer to be removed
     */
    public void removeOffer(String offerID){
        for(int i = 0;i<this.jobOffers.size();i++){
            if(this.jobOffers.get(i).equals(offerID)){
                this.jobOffers.remove(i);
                break;
            }
        }
    }
}
