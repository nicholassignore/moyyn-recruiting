package com.moyyn.recruiting.controller;

import com.moyyn.recruiting.model.Skill;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Skills {

    private List<Skill> skills = new ArrayList<>();

}
