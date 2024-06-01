package com.esprit.jobfinder.repository;


import com.esprit.jobfinder.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Integer> {
}