package com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.saved;

import com.hhsfbla.hhs_fbla_mad_2021.classes.Business;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Experience;
import com.hhsfbla.hhs_fbla_mad_2021.classes.JobOffer;

public class SavedRVModel {

    private JobOffer job;

    /**
     * Adds a saved job offer
     * @param jobOffer the offer
     */
    public void addJob(JobOffer jobOffer) {
        this.job = job;
    }

    /**
     * Returns the job title
     * @return the job title
     */
    public String getJobTitle() {
        return job.getJobTitle();
    }

    /**
     * Returns the business firebase ID
     * @return the busienss firebase ID
     */
    public String getbusinessID() {
        return job.getBusinessID();
    }

    /**
     * Returns the application link
     * @return the application link
     */
    public String getLink() {
        return job.getLink();
    }


    /**
     *
     * @param job the job offer to construct the model around
     */
    public SavedRVModel(JobOffer job) {
        this.job = job;
    }
}
