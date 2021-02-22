package com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.experiences;

import com.hhsfbla.hhs_fbla_mad_2021.classes.Business;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Experience;

public class ExperiencesRVModel {

    private Experience experience;


    public void addExperience(Experience experience) {
        this.experience = experience;
    }

    public String getHeader() {
        if(experience.isCurrentlyWorking()){
            return experience.getTitle() + " | " + experience.getStartTime() + "- Present";
        }
        return experience.getTitle() + " | " + experience.getStartTime() + " - " + experience.getEndTime();
    }
    public String getWorkplace() {
        return experience.getWorkplace();
    }
    public String getEndTime() {
        return experience.getEndTime();
    }
    public boolean isCurrentlyWorking() { return experience.isCurrentlyWorking(); }
    public String getStartTime() { return experience.getStartTime(); }
    public String getDescription(){return experience.getDescription();}


    public ExperiencesRVModel(Experience experience) {
        this.experience = experience;
    }
}
