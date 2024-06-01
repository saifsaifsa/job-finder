package com.esprit.jobfinder.services;

import com.esprit.jobfinder.models.Skill;

import java.util.List;

public interface SkillService {
    Skill save(Skill skill);
    List<Skill> findAll();
    Skill findById(int id);
}
