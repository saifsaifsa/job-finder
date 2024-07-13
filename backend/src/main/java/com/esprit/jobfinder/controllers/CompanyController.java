package com.esprit.jobfinder.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.esprit.jobfinder.exceptions.BadRequestException;
import com.esprit.jobfinder.models.User;
import com.esprit.jobfinder.models.enums.ERole;
import com.esprit.jobfinder.payload.request.CreateUserReq;
import com.esprit.jobfinder.payload.request.UpdateUserReq;
import com.esprit.jobfinder.utiles.DateUtils;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.esprit.jobfinder.payload.request.CreateCompanyReq;


import com.esprit.jobfinder.exceptions.ResourceNotFoundException;
import com.esprit.jobfinder.models.Company;
import com.esprit.jobfinder.services.ICompanyService;

// add cross origin *
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/company")
public class CompanyController {


    private final ICompanyService companyService;

    public CompanyController(ICompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping( consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Company> createCompany(@Valid @ModelAttribute CreateCompanyReq createReq) {
        Company companyDetails = new Company();
        companyDetails.setName(createReq.getName());
        companyDetails.setLocation(createReq.getLocation());
        companyDetails.setDescription(createReq.getDescription());
        companyDetails.setIndustry(createReq.getIndustry());
        companyDetails.setRating(createReq.getRating());
        Company createdCompany = companyService.createCompany(companyDetails,createReq.getImage());
        return ResponseEntity.ok(createdCompany);
    }

    @PutMapping(path="/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Company> updateCompany(@PathVariable int id, @Valid @ModelAttribute CreateCompanyReq updateReq) {
        Company companyDetails = new Company();
        companyDetails.setId(id);
        companyDetails.setName(updateReq.getName());
        companyDetails.setLocation(updateReq.getLocation());
        companyDetails.setDescription(updateReq.getDescription());
        companyDetails.setIndustry(updateReq.getIndustry());
        companyDetails.setRating(updateReq.getRating());
        Company updatedCompany = null;
        updatedCompany = companyService.updateCompany(id,companyDetails,updateReq.getImage());
        return ResponseEntity.ok(updatedCompany);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable int id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable int id) {
        return companyService.getCompanyById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("company not found with id " + id));
    }

    @PostMapping("/addRating")
    public ResponseEntity<String> addRating() {
        return ResponseEntity.ok("add rating .....");
    }

    @PostMapping("/addReview")
    public ResponseEntity<String> addReview() {
        return ResponseEntity.ok("add review .....");
    }

    @PostMapping("/sendCompanyMailConfirmation")
    public ResponseEntity<String> sendCompanyMailConfirmation() {

        return ResponseEntity.ok("mail confirmation sending  .....");
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getUsersStats() {
        return ResponseEntity.ok(companyService.getCompanyStatistics());
    }
}

