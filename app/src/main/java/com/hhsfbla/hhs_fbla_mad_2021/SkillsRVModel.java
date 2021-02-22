package com.hhsfbla.hhs_fbla_mad_2021;

import com.hhsfbla.hhs_fbla_mad_2021.classes.Business;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Education;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Experience;

public class SkillsRVModel {

    private String skill;


    public void addExperience(String skill) {
        this.skill = skill;
    }

    public String getSkill() {
        return skill;
    }
    public SkillsRVModel(String skill) {
        this.skill = skill;
    }
}
