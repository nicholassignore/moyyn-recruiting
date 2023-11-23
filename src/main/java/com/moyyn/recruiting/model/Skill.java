package com.moyyn.recruiting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;


@Entity
@Slf4j
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String name;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST,
                    CascadeType.MERGE},
            mappedBy = "skills")
    @JsonIgnore
    private Set<Candidate> candidates = new HashSet<>(); //<--- mickey mouse

    public Skill(String name) {
        this.setName(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Skill skill = (Skill) o;

        return name.equals(skill.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
