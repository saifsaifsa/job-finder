package com.esprit.jobfinder.services;

import com.esprit.jobfinder.models.Competence;
import com.esprit.jobfinder.models.Cv;
import org.springframework.web.multipart.MultipartFile;

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
    Cv addCompetenceToCv(Long cvId, Competence competence);
    Cv removeCompetenceFromCv(Long cvId, Long competenceId);
    Map<String, Long> getCvStatistics();
    Cv uploadCvPdf(Long userId, MultipartFile file);

}
