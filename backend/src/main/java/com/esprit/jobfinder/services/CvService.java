package com.esprit.jobfinder.services;

import com.esprit.jobfinder.models.Cv;

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
}
