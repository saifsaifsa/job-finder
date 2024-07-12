package com.esprit.jobfinder.payload.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateCompanyReq {

    private MultipartFile image;


    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotBlank(message = "location cannot be blank")
    private String location;
    private Double rating;
    private String industry;

}