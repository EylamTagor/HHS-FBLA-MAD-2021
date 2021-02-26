package com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.education;

import com.hhsfbla.hhs_fbla_mad_2021.classes.Education;

public class EducationRVModel {

    private Education education;

    /**
     * Adds an education object
     * @param education the object
     */
    public void addEducation(Education education) {
        this.education = education;
    }

    /**
     * Returns the period of time
     * @return the period of time
     */
    public String getPeriod() {
        if(education.isCurrent()) {
            return education.getStart() + " - Present";
        }
        else{
            return education.getStart() + " - " + education.getEnd();
        }
    }

    /**
     * Returns the school
     * @return the school
     */
    public String getSchool() {
        return education.getSchool();
    }

    /**
     * Returns the degree achieved
     * @return the degree achieved
     */
    public String getDegree() {
        return education.getDegree();
    }

    /**
     *
     * @param education The education object to construct the model around
     */
    public EducationRVModel(Education education) {
        this.education = education;
    }

}
