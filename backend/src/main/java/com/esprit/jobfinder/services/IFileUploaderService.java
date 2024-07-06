package com.esprit.jobfinder.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileUploaderService {
    public String uploadFile(MultipartFile file) throws IOException;
}
