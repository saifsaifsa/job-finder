package com.esprit.jobfinder.controllers;

import com.esprit.jobfinder.models.Competence;
import com.esprit.jobfinder.models.Cv;
import com.esprit.jobfinder.services.CvService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/cv")
@RequiredArgsConstructor
public class CvController {
    private final CvService cvService;

    @PostMapping
    public Cv createCv(@RequestBody Cv cv) {
        return cvService.createCv(cv);
    }

    @GetMapping
    public List<Cv> getAllCvs() {
        return cvService.getAllCvs();
    }

    @GetMapping("/{id}")
    public Cv getCv(@PathVariable Long id) {
        Cv cv = cvService.getCv(id);
        if (cv != null) {
            return cv;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CV not found");
    }

    @PutMapping("/{id}")
    public Cv updateCv(@PathVariable Long id, @RequestBody Cv cvDetails) {
        Cv cv = cvService.getCv(id);
        if (cv != null) {
            cv.setContent(cvDetails.getContent());
            return cvService.updateCv(cv);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CV not found");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCv(@PathVariable Long id) {
        cvService.deleteCv(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/increment-views")
    public ResponseEntity<Void> incrementViews(@PathVariable Long id) {
        cvService.incrementViews(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/competences")
    public Cv addCompetenceToCv(@PathVariable Long id, @RequestBody Competence competence) {
        return cvService.addCompetenceToCv(id, competence);
    }

    @DeleteMapping("/{id}/competences/{competenceId}")
    public Cv removeCompetenceFromCv(@PathVariable Long id, @PathVariable Long competenceId) {
        return cvService.removeCompetenceFromCv(id, competenceId);
    }

    @GetMapping("/{id}/export-pdf")
    public ResponseEntity<byte[]> exportCvToPDF(@PathVariable Long id) {
        byte[] pdfData = cvService.exportCvToPDF(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "cv.pdf");
        return new ResponseEntity<>(pdfData, headers, HttpStatus.OK);
    }

    @GetMapping("/stats")
    public Map<String, Long> getCvStatistics() {
        return cvService.getCvStatistics();
    }

    @GetMapping("/{id}/increment-downloads")
    public ResponseEntity<Void> incrementDownloads(@PathVariable Long id) {
        cvService.incrementDownloads(id);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/upload")
    public ResponseEntity<Cv> uploadCv(@RequestParam("userId") Long userId, @RequestParam("file") MultipartFile file) {
        Cv cv = cvService.uploadCvPdf(userId, file);
        return ResponseEntity.ok(cv);
    }
}
