package com.moyyn.recruiting.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moyyn.recruiting.model.Skills;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;


@Slf4j
public class SkillsTest {


    ObjectMapper mapper = new ObjectMapper();

    @Test
    void create_skills_request() throws JsonProcessingException {

        Skills skills = new Skills();
        skills.getSkills().add("react");

        String s = mapper.writeValueAsString(skills);
        log.info("s : {} ", s);

    }
}
