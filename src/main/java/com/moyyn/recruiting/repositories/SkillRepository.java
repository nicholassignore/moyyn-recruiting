package com.moyyn.recruiting.repositories;

import com.moyyn.recruiting.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    // getter
    List<Skill> findSkillByCandidatesId(Long tutorialId);

    Optional<Skill> findByName(String name);
}
