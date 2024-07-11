package com.esprit.jobfinder.services;

import java.util.List;
import java.util.Optional;

import com.esprit.jobfinder.models.Company;

public interface ICompanyService {
    Company createCompany(Company company);
    Optional<Company> updateCompany(int id, Company companyDetails);
    void deleteCompany(int id);
    List<Company> getAllCompanies();
    Optional<Company> getCompanyById(int id);
}
