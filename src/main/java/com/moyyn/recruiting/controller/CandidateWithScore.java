package com.moyyn.recruiting.controller;

import com.moyyn.recruiting.model.Candidate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CandidateWithScore {
    private Candidate candidate;
    private Integer score;
}
