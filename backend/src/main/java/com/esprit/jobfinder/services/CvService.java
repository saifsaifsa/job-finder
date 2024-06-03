package com.esprit.jobfinder.services;

import com.esprit.jobfinder.models.Cv;
import com.esprit.jobfinder.models.Skill;

import java.util.List;

public interface CvService {
    Cv createCv(Cv cv);
    List<Cv> getAllCvs();
    Cv getCv(Long id);
    Cv updateCv(Cv cv);
    void deleteCv(Long id);
    void incrementViews(Long id);
    void incrementDownloads(Long id);
    byte[] exportCvToPDF(Long id);
    Cv addSkillToCv(Long cvId, Skill skill);
    Cv removeSkillFromCv(Long cvId, Long skillId);
}
