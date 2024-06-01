package com.esprit.jobfinder.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esprit.jobfinder.models.Skill;
import com.esprit.jobfinder.services.SkillService;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillService skillService;

    
    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping
    public Skill createSkill(@RequestBody Skill skill) {
        return skillService.save(skill);
    }

    @GetMapping
    public List<Skill> getAllSkills() {
        return skillService.findAll();
    }

    @GetMapping("/{id}")
    public Skill getSkillById(@PathVariable int id) {
        return skillService.findById(id);
    }
}