package com.esprit.jobfinder.repository;


import com.esprit.jobfinder.models.Training;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esprit.jobfinder.models.Company;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    @Query("SELECT t FROM Company t ORDER BY t.rating DESC")
    List<Company> findMostLikedCompanies(Pageable pageable);

    @Query("SELECT c.industry, SUM(c.rating) as totalLikes FROM Company c GROUP BY c.industry ORDER BY totalLikes DESC")
    List<Object[]> findMostPopularIndustry();

    @Query("SELECT c.industry, COUNT(c) as companyCount FROM Company c GROUP BY c.industry ORDER BY companyCount DESC")
    List<Object[]> findNumberOfCompaniesPerIndustry();
}
