package com.esprit.jobfinder.utiles;

import com.esprit.jobfinder.models.Competence;
import com.esprit.jobfinder.models.Participation;
import com.esprit.jobfinder.models.Quiz;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.tika.exception.EncryptedDocumentException;
import org.springframework.boot.context.ApplicationPidFileWriter;

import javax.swing.text.Document;
import javax.swing.text.ParagraphView;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class PDFExporter {

    public static byte[] exportCompetencesToPDF(List<Competence> competences) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();
            for (Competence competence : competences) {
                document.add(new ParagraphView(competence.getName()));
                // Add more fields as needed
            }
            document.close();
        } catch (EncryptedDocumentException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

    public static byte[] exportQuizzesToPDF(List<Quiz> quizzes) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            ApplicationPidFileWriter.getInstance(document, out);
            document.open();
            for (Quiz quiz : quizzes) {
                document.add(new ParagraphView(quiz.getLabel()));
                // Add more fields as needed
            }
            document.close();
        } catch (EncryptedDocumentException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

    public static byte[] exportResultsToPDF(List<Participation> participations) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            ApplicationPidFileWriter.getInstance(document, out);
            document.open();
            for (Participation participation : participations) {
                document.add(new ParagraphView("User ID: " + participation.getUserId()));
                document.add(new ParagraphView("Quiz ID: " + participation.getQuizId()));
                document.add(new ParagraphView("Score: " + participation.getScore()));
                // Add more fields as needed
            }
            document.close();
        } catch (EncryptedDocumentException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }
}
