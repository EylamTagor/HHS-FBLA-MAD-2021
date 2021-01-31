package com.hhsfbla.hhs_fbla_mad_2021.classes;

public class Experience {

    private String title;
    private Business workplace;
    private String startTime;
    private String endTime;
    private boolean currentlyWorking;

    public Experience(String title, Business workplace, String startTime, String endTime, boolean currentlyWorking){
        this.title = title;
        this.workplace = workplace;
        this.startTime = startTime;
        this.endTime = endTime;
        this.currentlyWorking = currentlyWorking;
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

    public Business getWorkplace() {
        return workplace;
    }

    public void setWorkplace(Business workplace) {
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

}
