package com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.saved;

import com.hhsfbla.hhs_fbla_mad_2021.classes.Business;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Experience;
import com.hhsfbla.hhs_fbla_mad_2021.classes.JobOffer;

public class SavedRVModel {

    private JobOffer job;
    public void addJob(JobOffer jobOffer) {
        this.job = job;
    }

    public String getJobTitle() {
        return job.getJobTitle();
    }
    public String getbusinessID() {
        return job.getBusinessID();
    }
    public String getLink() {
        return job.getLink();
    }



    public SavedRVModel(JobOffer job) {
        this.job = job;
    }
}
