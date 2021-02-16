package com.hhsfbla.hhs_fbla_mad_2021.classes;

public class JobOffer {

    private String businessID;
    private String jobTitle;
    private String link;
    private String jobDescription;

    public JobOffer(String businessID, String jobTitle, String link, String jobDescription){
        this.businessID = businessID;
        this.jobTitle = jobTitle;
        this.link = link;
        this.jobDescription = jobDescription;
    }

    public String getBusinessID() {
        return businessID;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getLink() {
        return link;
    }

    public void setBusinessID(String businessID) {
        this.businessID = businessID;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
