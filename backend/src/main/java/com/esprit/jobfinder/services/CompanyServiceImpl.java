package com.esprit.jobfinder.services;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.esprit.jobfinder.exceptions.NotFoundException;
import com.esprit.jobfinder.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esprit.jobfinder.models.Company;
import com.esprit.jobfinder.repository.CompanyRepository;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CompanyServiceImpl implements ICompanyService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private IFileUploaderService fileUploaderService;
    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company createCompany(Company company, MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            String filePath;
            try {
                filePath = fileUploaderService.uploadFile(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            company.setImage(filePath);
        }

        emailService.sendSimpleMessage("trabelsi.mouhib3@gmail.com", "Email confirmation", "your company"+ " "+company.getName()+" "+"created successfully, you can create many offers now.");
        return companyRepository.save(company);
    }

    @Override
    public Company updateCompany(int id, Company companyDetails, MultipartFile image) {
        Company existingCompany = companyRepository.findById(companyDetails.getId())
                .orElseThrow(() -> new NotFoundException("User not found with id: " + companyDetails.getId()));        String filePath = null;

        if (image != null && !image.isEmpty()) {
            try {
                filePath = fileUploaderService.uploadFile(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String finalFilePath = filePath;
        existingCompany.setName(companyDetails.getName());
        existingCompany.setDescription(companyDetails.getDescription());
        existingCompany.setIndustry(companyDetails.getIndustry());
        existingCompany.setLocation(companyDetails.getLocation());
        existingCompany.setRating(companyDetails.getRating());
        existingCompany.setImage(finalFilePath);

        return companyRepository.save(existingCompany);

    }

    @Override
    public void deleteCompany(int id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("company not found with id " + id));
        companyRepository.delete(company);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Optional<Company> getCompanyById(int id) {
        return companyRepository.findById(id);
    }
}
