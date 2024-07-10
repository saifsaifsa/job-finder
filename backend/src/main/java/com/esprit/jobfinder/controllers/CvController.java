package com.esprit.jobfinder.controllers;

import com.esprit.jobfinder.models.Cv;
import com.esprit.jobfinder.models.Skill;
import com.esprit.jobfinder.services.CvService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/{id}/skills")
    public Cv addSkillToCv(@PathVariable Long id, @RequestBody Skill skill) {
        return cvService.addSkillToCv(id, skill);
    }

    @DeleteMapping("/{id}/skills/{skillId}")
    public Cv removeSkillFromCv(@PathVariable Long id, @PathVariable Long skillId) {
        return cvService.removeSkillFromCv(id, skillId);
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

}
