package com.moyyn.recruiting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String fileName;
    private String fileType;
    private String firstName;
    private String lastName;
    private Integer age;
    private Boolean married;

    // X cross table o join table
    @ManyToMany(fetch = FetchType.LAZY, cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE})
    @JoinTable(name = "candidate_skills",
            joinColumns = { @JoinColumn(name = "candidate_id") },
            inverseJoinColumns = { @JoinColumn(name = "skill_id") })
    @JsonIgnore
    private Set<Skill> skills = new HashSet<>();


    public void addSkill(Skill skill) {
        this.skills.add(skill);   //  <---- java
        skill.getCandidates().add(this);
    }

    public void removeSkill(long skillId) {
        Skill skill = this.skills.stream().filter(t -> t.getId() == skillId).findFirst().orElse(null);
        if (skill != null) {
            this.skills.remove(skill);
            skill.getCandidates().remove(this);
        }
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", married=" + married +
                '}';
    }
}
