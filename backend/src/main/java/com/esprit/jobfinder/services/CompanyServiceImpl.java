package com.esprit.jobfinder.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.esprit.jobfinder.models.Company;
import com.esprit.jobfinder.repository.CompanyRepository;

@Service
public class CompanyServiceImpl implements ICompanyService {


    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Optional<Company> updateCompany(int id, Company companyDetails) {
        return companyRepository.findById(id).map(company -> {
            company.setName(companyDetails.getName());
            company.setDescription(companyDetails.getDescription());
            company.setIndustry(companyDetails.getIndustry());
            company.setLocation(companyDetails.getLocation());
            company.setRating(companyDetails.getRating());


            return companyRepository.save(company);
        });
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
