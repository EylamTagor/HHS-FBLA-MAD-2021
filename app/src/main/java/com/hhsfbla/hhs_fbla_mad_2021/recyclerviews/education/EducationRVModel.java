package com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.education;

import com.hhsfbla.hhs_fbla_mad_2021.classes.Education;

public class EducationRVModel {

    private Education education;


    public void addEducation(Education education) {
        this.education = education;
    }

    public String getPeriod() {
        if(education.isCurrent()) {
            return education.getStart() + " - Present";
        }
        else{
            return education.getStart() + " - " + education.getEnd();
        }
    }
    public String getSchool() {
        return education.getSchool();
    }
    public String getDegree() {
        return education.getDegree();
    }

    public EducationRVModel(Education education) {
        this.education = education;
    }

}
