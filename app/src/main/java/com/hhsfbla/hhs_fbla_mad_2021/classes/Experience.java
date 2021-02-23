package com.hhsfbla.hhs_fbla_mad_2021.classes;

/**
 * Represents an experience a user has completed
 *
 */
public class Experience {

    private String title;
    private String workplace;
    private String startTime;
    private String endTime;
    private String description;
    private boolean currentlyWorking;

    /**
     * No args constructor
     */
    public Experience(){
        this("", "", "", "", "", false);
    }

    /**
     *
     * @param title The title of the experience
     * @param workplace Where the user worked
     * @param startTime When the user started working
     * @param endTime When the user ended working
     * @param description A description of the experience
     * @param currentlyWorking Whether the user is currently working
     */
    public Experience(String title, String workplace, String startTime, String endTime, String description, boolean currentlyWorking){
        this.currentlyWorking = currentlyWorking;
        this.title = title;
        this.workplace = workplace;
        this.startTime = startTime;
        this.endTime = currentlyWorking? "Present" : endTime;
        this.description = description;
    }

    /**
     * Sets the new title of the experience
     * @param title the new title of the experience
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the title of the experience
     * @return the title of the experience
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns whether the user is currently working
     * @return whether the user is currently working
     */
    public boolean isCurrentlyWorking() {
        return currentlyWorking;
    }

    /**
     * Sets whether the user is currently working
     * @param currentlyWorking whether the user is currently working
     */
    public void setCurrentlyWorking(boolean currentlyWorking) {
        this.currentlyWorking = currentlyWorking;
    }

    /**
     * Returns the workplace
     * @return the workplace
     */
    public String getWorkplace() {
        return workplace;
    }

    /**
     * Sets the workplace
     * @param workplace the new workplace
     */
    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    /**
     * Returns the ending time
     * @return the ending time
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Returns the starting time
     * @return the starting time
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Sets the end time
     * @param endTime the new end time
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * Sets the start time
     * @param startTime the new start time
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Returns the description
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the experience description
     * @param description the new experience description
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
