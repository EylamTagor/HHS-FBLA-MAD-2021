package com.hhsfbla.hhs_fbla_mad_2021.classes;

/**
 * This class represents a JobOffer that a Business can create and users can apply for
 *
 *
 */
public class JobOffer {

    private String businessID;
    private String jobTitle;
    private String link;
    private String jobDescription;

    /**
     * No args constructor
     */
    public JobOffer(){
        this("", "", "", "");
    }

    /**
     *
     * @param businessID the firebaseID of the business offering the job
     * @param jobTitle the title of the job being offered
     * @param link the link to the job application
     * @param jobDescription the description of the job
     */
    public JobOffer(String businessID, String jobTitle, String link, String jobDescription){
        this.businessID = businessID;
        this.jobTitle = jobTitle;
        this.link = link;
        this.jobDescription = jobDescription;
    }

    /**
     * Returns the business ID
     * @return the business ID
     */
    public String getBusinessID() {
        return businessID;
    }

    /**
     * Returns the job description
     * @return the job description
     */
    public String getJobDescription() {
        return jobDescription;
    }

    /**
     * Returns the job title
     * @return the job title
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * Returns the job application link
     * @return the job application link
     */
    public String getLink() {
        return link;
    }

    /**
     * Sets the business ID
     * @param businessID the new business ID
     */
    public void setBusinessID(String businessID) {
        this.businessID = businessID;
    }

    /**
     * Sets the job description
     * @param jobDescription the new job description
     */
    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    /**
     * Sets the new job title
     * @param jobTitle the new job title
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * Sets the new application link
     * @param link the new application link
     */
    public void setLink(String link) {
        this.link = link;
    }
}
