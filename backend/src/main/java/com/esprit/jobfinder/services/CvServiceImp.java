package com.esprit.jobfinder.services;

import com.esprit.jobfinder.models.Cv;
import com.esprit.jobfinder.models.Skill;
import com.esprit.jobfinder.repository.CvRepository;
import com.esprit.jobfinder.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
        Cv cv = cvRepository.findById(id).orElseThrow(() -> new RuntimeException("CV not found"));

        PDDocument document = new PDDocument();
        try {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            float yPosition = page.getMediaBox().getHeight() - 50;
            float margin = 50;


            addHeading(contentStream, "CV Details", margin, yPosition);
            yPosition -= 30;

            addSection(contentStream, "Name", cv.getUser().getFullName(), margin, yPosition);
            yPosition -= 20;

            addSection(contentStream, "Email", cv.getUser().getEmail(), margin, yPosition);
            yPosition -= 20;

            addSection(contentStream, "Content", cv.getContent(), margin, yPosition);
            yPosition -= 20;

            addSection(contentStream, "Views", String.valueOf(cv.getViews()), margin, yPosition);
            yPosition -= 20;

            addSection(contentStream, "Downloads", String.valueOf(cv.getDownloads()), margin, yPosition);
            yPosition -= 20;

            contentStream.close();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            document.close();

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating PDF");
        }
    }

    private void addHeading(PDPageContentStream contentStream, String text, float x, float y) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(text);
        contentStream.endText();
    }

    private void addSection(PDPageContentStream contentStream, String label, String value, float x, float y) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(label + ": ");
        contentStream.showText(value);
        contentStream.endText();
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


}
