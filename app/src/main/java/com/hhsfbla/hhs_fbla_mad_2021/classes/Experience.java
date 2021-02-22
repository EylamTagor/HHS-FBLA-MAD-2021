package com.hhsfbla.hhs_fbla_mad_2021.classes;

public class Experience {

    private String title;
    private String workplace;
    private String startTime;
    private String endTime;
    private String description;
    private boolean currentlyWorking;

    public Experience(){
        this("", "", "", "", "", false);
    }

    public Experience(String title, String workplace, String startTime, String endTime, String description, boolean currentlyWorking){
        this.title = title;
        this.workplace = workplace;
        this.startTime = startTime;
        this.endTime = endTime;
        this.currentlyWorking = currentlyWorking;
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCurrentlyWorking() {
        return currentlyWorking;
    }

    public void setCurrentlyWorking(boolean currentlyWorking) {
        this.currentlyWorking = currentlyWorking;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
