package com.hhsfbla.hhs_fbla_mad_2021.classes;

import android.widget.ImageView;

import java.util.ArrayList;

//Temp stuff here so I can do profile page stuff

public class Business {
    private String name;
    private int logo;
    private ArrayList<String> jobOffers;

    public Business(String name, int logo){
        this.name = name;
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setTitle(String name) {
        this.name = name;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public void addJobOffer(String JOID){
        jobOffers.add(JOID);
    }

    public ArrayList<String> getJobOffers() {
        return jobOffers;
    }

    public void removeOffer(String offerID){
        for(int i = 0;i<this.jobOffers.size();i++){
            if(this.jobOffers.get(i).equals(offerID)){
                this.jobOffers.remove(i);
                break;
            }
        }
    }
}
