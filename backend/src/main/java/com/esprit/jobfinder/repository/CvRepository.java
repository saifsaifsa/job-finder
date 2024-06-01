package com.esprit.jobfinder.repository;

import com.esprit.jobfinder.models.Cv;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CvRepository extends JpaRepository<Cv,Long> {
}
