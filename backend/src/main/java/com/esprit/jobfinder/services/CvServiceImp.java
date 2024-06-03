package com.esprit.jobfinder.services;

import com.esprit.jobfinder.models.Cv;
import com.esprit.jobfinder.models.Skill;
import com.esprit.jobfinder.repository.CvRepository;
import com.esprit.jobfinder.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CvServiceImp implements CvService {
    private final CvRepository cvRepository;
    private final SkillRepository skillRepository;

    @Override
    public Cv createCv(Cv cv) {
        return cvRepository.save(cv);
    }

    @Override
    public List<Cv> getAllCvs() {
        return cvRepository.findAll();
    }

    @Override
    public Cv getCv(Long id) {
        return cvRepository.findById(id).orElse(null);
    }

    @Override
    public Cv updateCv(Cv cv) {
        return cvRepository.save(cv);
    }

    @Override
    public void deleteCv(Long id) {
        cvRepository.deleteById(id);
    }

    @Override
    public void incrementViews(Long id) {
        cvRepository.findById(id).ifPresent(cv -> {
            cv.setViews(cv.getViews() + 1);
            cvRepository.save(cv);
        });
    }

    @Override
    public void incrementDownloads(Long id) {
        // Implement this method if needed
    }

    @Override
    public byte[] exportCvToPDF(Long id) {
        // Implement this method if needed
        return new byte[0];
    }

    @Override
    public Cv addSkillToCv(Long cvId, Skill skill) {
        Cv cv = cvRepository.findById(cvId).orElseThrow(() -> new RuntimeException("CV not found"));
        Skill existingSkill = skillRepository.findById(skill.getId()).orElseThrow(() -> new RuntimeException("Skill not found"));
        cv.getSkills().add(existingSkill);
        return cvRepository.save(cv);
    }

    @Override
    public Cv removeSkillFromCv(Long cvId, Long skillId) {
        return null;
    }

  /*   @Override
   public Cv removeSkillFromCv(Long cvId, Long skillId) {
        /*Cv cv = cvRepository.findById(cvId).orElseThrow(() -> new RuntimeException("CV not found"));
        Skill skill = skillRepository.findById(skillId).orElseThrow(() -> new RuntimeException("Skill not found"));
        cv.getSkills().remove(skill);
        return cvRepository.save(cv);
    }*/
}
