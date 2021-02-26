package com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.experiences;

import com.hhsfbla.hhs_fbla_mad_2021.classes.Business;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Experience;

public class ExperiencesRVModel {

    private Experience experience;

    /**
     * Adds an experience
     * @param experience the new experience
     */
    public void addExperience(Experience experience) {
        this.experience = experience;
    }

    /**
     * Returns the header
     * @return the header
     */
    public String getHeader() {
        if(experience.isCurrentlyWorking()){
            return experience.getTitle() + " | " + experience.getStartTime() + " - Present";
        }
        return experience.getTitle() + " | " + experience.getStartTime() + " - " + experience.getEndTime();
    }

    /**
     * Returns the workplace
     * @return the workplace
     */
    public String getWorkplace() {
        return experience.getWorkplace();
    }

    /**
     * Returns the end time
     * @return the end time
     */
    public String getEndTime() {
        return experience.getEndTime();
    }

    /**
     * Returns whether the user is currently working at this experience
     * @return whether the user is currently working at this experience
     */
    public boolean isCurrentlyWorking() { return experience.isCurrentlyWorking(); }

    /**
     * Returns the start time
     * @return the start time
     */
    public String getStartTime() { return experience.getStartTime(); }

    /**
     * Returns the description
     * @return the description
     */
    public String getDescription(){return experience.getDescription();}

    /**
     *
     * @param experience the experience to construct the model around
     */
    public ExperiencesRVModel(Experience experience) {
        this.experience = experience;
    }
}
