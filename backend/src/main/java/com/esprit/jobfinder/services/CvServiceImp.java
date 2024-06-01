package com.esprit.jobfinder.services;

import com.esprit.jobfinder.models.Cv;
import com.esprit.jobfinder.repository.CvRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CvServiceImp implements CvService {
    public CvServiceImp(CvRepository cvRepository) {
        this.cvRepository = cvRepository;
    }

    private CvRepository cvRepository;

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

    }

    @Override
    public byte[] exportCvToPDF(Long id) {
        return new byte[0];
    }


}
