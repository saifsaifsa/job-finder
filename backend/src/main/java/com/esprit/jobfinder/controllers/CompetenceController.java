package com.esprit.jobfinder.controllers;

import com.esprit.jobfinder.models.Competence;
import com.esprit.jobfinder.services.CompetenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/competences")
public class CompetenceController {
    @Autowired
    private CompetenceService competenceService;

    @GetMapping
    public List<Competence> getAllCompetences() {
        return competenceService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Competence> getCompetenceById(@PathVariable Long id) {
        Competence competence = competenceService.findById(id);
        if (competence == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(competence);
    }

    @PostMapping
    public Competence createCompetence(@RequestBody Competence competence) {
        return competenceService.save(competence);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Competence> updateCompetence(@PathVariable Long id, @RequestBody Competence competenceDetails) {
        Competence competence = competenceService.findById(id);
        if (competence == null) {
            return ResponseEntity.notFound().build();
        }
        competence.setName(competenceDetails.getName());
        competence.setCategory(competenceDetails.getCategory());
        return ResponseEntity.ok(competenceService.save(competence));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompetence(@PathVariable Long id) {
        Competence competence = competenceService.findById(id);
        if (competence == null) {
            return ResponseEntity.notFound().build();
        }
        competenceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{category}")
    public List<Competence> getCompetencesByCategory(@PathVariable String category) {
        return competenceService.findByCategory(category);
    }

//    @GetMapping("/export")
//    public ResponseEntity<byte[]> exportCompetencesToPDF() {
//        List<Competence> competences = competenceService.findAll();
//        byte[] pdfContent = PDFExporter.exportCompetencesToPDF(competences);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDispositionFormData("attachment", "competences.pdf");
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(pdfContent);
//    }

    @PostMapping("/{id}/upload")
    public ResponseEntity<String> uploadFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        // Logique de sauvegarde de fichier...
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(file.getOriginalFilename())
                .toUriString();

        return ResponseEntity.status(HttpStatus.OK).body(fileDownloadUri);
    }
}
