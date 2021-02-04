package com.hhsfbla.hhs_fbla_mad_2021.classes;

import android.widget.ImageView;

//Temp stuff here so I can do profile page stuff

public class Business {
    private String name;
    private int logo;

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
}
