package com.hhsfbla.hhs_fbla_mad_2021.classes;

public class Education {
    private String school, start, end, degree;
    private boolean current;

    public Education() {
        school = "";
        start = "";
        end = "";
        degree = "";
        current = false;
    }

    public Education(String school, String start, String end, String degree, boolean current) {
        this.current = current;
        this.school = school;
        this.start = start;
        this.end = current? "Present" : end;
        this.degree = degree;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }
}