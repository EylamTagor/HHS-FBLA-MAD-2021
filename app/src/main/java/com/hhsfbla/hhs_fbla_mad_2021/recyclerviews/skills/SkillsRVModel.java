package com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.skills;

import com.hhsfbla.hhs_fbla_mad_2021.classes.Business;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Education;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Experience;

public class SkillsRVModel {

    private String skill;

    /**
     * Adds a skill
     * @param skill the new skill
     */
    public void addSkill(String skill) {
        this.skill = skill;
    }

    /**
     * Returns the skill
     * @return the skill
     */
    public String getSkill() {
        return skill;
    }

    /**
     *
     * @param skill the skill to be created
     */
    public SkillsRVModel(String skill) {
        this.skill = skill;
    }
}
