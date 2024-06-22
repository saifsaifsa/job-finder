package com.esprit.jobfinder.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esprit.jobfinder.models.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
