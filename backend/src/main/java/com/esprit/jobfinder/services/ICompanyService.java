package com.esprit.jobfinder.services;

import java.util.List;
import java.util.Optional;

import com.esprit.jobfinder.models.Company;
import org.springframework.web.multipart.MultipartFile;

public interface ICompanyService {
    Company createCompany(Company company, MultipartFile image);
    Company updateCompany(int id, Company companyDetails, MultipartFile image);
    void deleteCompany(int id);
    List<Company> getAllCompanies();
    Optional<Company> getCompanyById(int id);
}
