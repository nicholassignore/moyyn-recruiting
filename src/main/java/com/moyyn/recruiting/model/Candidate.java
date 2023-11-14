package com.moyyn.recruiting.model;

import lombok.Data;

import java.util.List;


@Data
public class Candidate {

    private String fileName;
    private String fileType;
    private String firstName;
    private String lastName;
    private Integer age;
    private Boolean married;
    private List<String> skills;
}
