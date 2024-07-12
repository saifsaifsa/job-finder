package com.esprit.jobfinder.repository;

import com.esprit.jobfinder.models.Competence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CompetenceRepository extends JpaRepository<Competence, Long> {
    List<Competence> findByCategory(String category);
    @Query("SELECT s FROM Competence s WHERE s.id IN :ids")
    List<Competence> findByIds(@Param("ids") Set<Long> ids);
}
