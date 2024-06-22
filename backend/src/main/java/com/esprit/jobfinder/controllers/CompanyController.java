package com.esprit.jobfinder.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esprit.jobfinder.exceptions.ResourceNotFoundException;
import com.esprit.jobfinder.models.Company;
import com.esprit.jobfinder.services.ICompanyService;

// add cross origin *
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/companies")
public class CompanyController {


    private final ICompanyService companyService;

    public CompanyController(ICompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        Company createdCompany = companyService.createCompany(company);
        return ResponseEntity.ok(createdCompany);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable int id, @RequestBody Company companyDetails) {
        return companyService.updateCompany(id, companyDetails)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("company not found with id " + id));
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
}

