package com.esprit.jobfinder.services;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileUploaderService implements IFileUploaderService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Failed to store empty file.");
        }
        Path rootDir = Paths.get(uploadDir);
        if (!Files.exists(rootDir)) {
            Files.createDirectories(rootDir);
        }
        Path filePath = rootDir.resolve(file.getOriginalFilename());
        byte[] bytes = file.getBytes();
        Files.write(filePath, bytes);
        return filePath.toString();
    }
}
