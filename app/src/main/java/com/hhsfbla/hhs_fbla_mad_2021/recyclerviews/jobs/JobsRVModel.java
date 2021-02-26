package com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.jobs;

import com.hhsfbla.hhs_fbla_mad_2021.classes.Business;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Experience;
import com.hhsfbla.hhs_fbla_mad_2021.classes.JobOffer;

public class JobsRVModel {

    private JobOffer job;

    /**
     * Adds a job offer
     * @param jobOffer the new job offer
     */
    public void addJob(JobOffer jobOffer) {
        this.job = job;
    }

    /**
     * Returns the time posted
     * @return the time posted
     */
    public long getTime(){return job.getTimePosted();}

    /**
     * Returns the job title
     * @return the job title
     */
    public String getJobTitle() {
        return job.getJobTitle();
    }

    /**
     * Returns the business ID
     * @return the business ID
     */
    public String getbusinessID() {
        return job.getBusinessID();
    }

    /**
     * Returns the link to the application
     * @return the link to the application
     */
    public String getLink() {
        return job.getLink();
    }

    /**
     * Returns the job description
     * @return the job description
     */
    public String getJobDescription() { return job.getJobDescription(); }

    /**
     * Returns the job offer
     * @return the job offer
     */
    public JobOffer getJob() {
        return job;
    }

    /**
     *
     * @param job The job offer to construct the model around
     */
    public JobsRVModel(JobOffer job) {
        this.job = job;
    }
}
