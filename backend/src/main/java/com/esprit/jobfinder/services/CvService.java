package com.esprit.jobfinder.services;

import com.esprit.jobfinder.models.Cv;
import com.esprit.jobfinder.models.Skill;

import java.util.List;
import java.util.Map;

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
    Map<String, Long> getCvStatistics();
}
