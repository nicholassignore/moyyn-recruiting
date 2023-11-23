package com.moyyn.recruiting.services;

import com.moyyn.recruiting.model.Candidate;
import com.moyyn.recruiting.model.Skill;

import java.util.HashSet;
import java.util.Set;

public class TestUtils {
    protected static void createSetSkills(Candidate candidate) {
        Set<Skill> skills = new HashSet<>();
        skills.add(new Skill("java"));
        skills.add(new Skill("spring"));
        skills.add(new Skill("react"));
        candidate.setSkills(skills);
    }
}
