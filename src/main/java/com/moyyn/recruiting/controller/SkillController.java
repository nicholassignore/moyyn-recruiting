package com.moyyn.recruiting.controller;

import com.moyyn.recruiting.model.Candidate;
import com.moyyn.recruiting.model.Skill;
import com.moyyn.recruiting.repositories.CandidateRepository;
import com.moyyn.recruiting.repositories.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SkillController {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private SkillRepository skillRepository;

    // restituisce tutti gli skills
    @GetMapping("/skills")
    public ResponseEntity<List<Skill>> getAllSkills() {
        List<Skill> skills = new ArrayList<Skill>();

        skillRepository.findAll().forEach(skills::add);

        if (skills.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(skills, HttpStatus.OK);
    }

    @GetMapping("/bestCandidateByScore")
    public ResponseEntity<Candidate> getCandidateWithHighestScore(      ) {

    }


    // restituisce tutti gli skills di un candidato con ID = candidateId
    @GetMapping("/candidates/{candidateId}/skills")
    public ResponseEntity<List<Skill>> getAllSkillsByCandidateId(@PathVariable(value = "candidateId") Long candidateId) {
        if (!candidateRepository.existsById(candidateId)) {
            throw new IllegalArgumentException("Not found Candidate with id = " + candidateId);
        }

        List<Skill> skills = skillRepository.findSkillByCandidatesId(candidateId);
        return new ResponseEntity<>(skills, HttpStatus.OK);
    }

    @GetMapping("/skills/{id}")
    public ResponseEntity<Skill> getSkillsById(@PathVariable(value = "id") Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found Skill with id = " + id));

        return new ResponseEntity<>(skill, HttpStatus.OK);
    }

    @GetMapping("/skills/{skillId}/candidates")
    public ResponseEntity<List<Candidate>> getAllCandidatesBySkillId(@PathVariable(value = "skillId") Long skillId) {
        if (!skillRepository.existsById(skillId)) {
            throw new IllegalArgumentException("Not found Skill  with id = " + skillId);
        }

        List<Candidate> candidates = candidateRepository.findCandidatesById(skillId);
        return new ResponseEntity<>(candidates, HttpStatus.OK);
    }

    @PostMapping("/candidates/{candidateId}/skills")
    public ResponseEntity<Skill> addSkill(@PathVariable(value = "candidateId") Long candidateId, @RequestBody Skill skillRequest) {

        Skill skill = candidateRepository.findById(candidateId).map(candidate -> {

            // ho trovato il candidato con ID = candidateId
            Long skillId = skillRequest.getId();

            // skill is existed
            if (skillId != null &&  skillId != 0L) {
                Skill _skill = skillRepository.findById(skillId)
                        .orElseThrow(() -> new IllegalArgumentException("Not found Skill with id = " + skillId));
                candidate.addSkill(_skill);
                candidateRepository.save(candidate);
                return _skill;
            }
            else {
                // add and create new Skill
                candidate.addSkill(skillRequest);
                return skillRepository.save(skillRequest);
            }

        }).orElseThrow(() -> new IllegalArgumentException("Not found Candidate with id = " + candidateId));

        return new ResponseEntity<>(skill, HttpStatus.CREATED);
    }

    @PutMapping("/skills/{id}")
    public ResponseEntity<Skill> updateSkill(@PathVariable("id") long id, @RequestBody Skill skillRequest) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("SkillId " + id + "not found"));

        skill.setName(skillRequest.getName());

        return new ResponseEntity<>(skillRepository.save(skill), HttpStatus.OK);
    }

    @DeleteMapping("/candidates/{candidateId}/skills/{skillId}")
    public ResponseEntity<HttpStatus> deleteSkillFromCandidate(@PathVariable(value = "candidateId") Long candidateId, @PathVariable(value = "skillId") Long skillId) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new IllegalArgumentException("Not found Candidate with id = " + candidateId));

        candidate.removeSkill(skillId);
        candidateRepository.save(candidate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/skills/{id}")
    public ResponseEntity<HttpStatus> deleteSkill(@PathVariable("id") long id) {
        skillRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
