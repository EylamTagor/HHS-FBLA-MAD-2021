package com.hhsfbla.hhs_fbla_mad_2021.classes;

/**
 * Represents an educational experience a user has
 * @author Ayush
 */
public class Education {
    private String school, start, end, degree;
    private boolean current;

    /**
     * Empty constructor
     */
    public Education() {
        this("", "", "", "", false);
    }

    /**
     *
     * @param school The school at which the education happened
     * @param start The start date of the educational period
     * @param end The end date of the educational period
     * @param degree The degree earned, including major
     * @param current Whether the user is currently in this school
     */
    public Education(String school, String start, String end, String degree, boolean current) {
        this.current = current;
        this.school = school;
        this.start = start;
        this.end = current? "Present" : end;
        this.degree = degree;
    }

    /**
     * Returns the school where the education happened
     * @return the school where the education happened
     */
    public String getSchool() {
        return school;
    }

    /**
     * Sets the school where the education happened
     * @param school the school where the education happened
     */
    public void setSchool(String school) {
        this.school = school;
    }

    /**
     * Returns the start time of the educational period
     * @return the start time of the educational period
     */
    public String getStart() {
        return start;
    }

    /**
     * Sets the start time of the education
     * @param start the start time
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * Returns the end time of the education
     * @return the end time of the education
     */
    public String getEnd() {
        return end;
    }

    /**
     * Sets the end time of the education
     * @param end the new end time of the education
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * Returns the degree achieved
     * @return the degree achieved
     */
    public String getDegree() {
        return degree;
    }

    /**
     * Sets the new degree achieved
     * @param degree the new degree achieved
     */
    public void setDegree(String degree) {
        this.degree = degree;
    }

    /**
     * Returns whether or not the user is still being educated
     * @return whether or not the user is still being educated
     */
    public boolean isCurrent() {
        return current;
    }

    /**
     * Sets if the user is done with their education
     * @param current whether the user is done with their education
     */
    public void setCurrent(boolean current) {
        this.current = current;
    }
}