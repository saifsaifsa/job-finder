package com.esprit.jobfinder.repository;

import com.esprit.jobfinder.models.Cv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CvRepository extends JpaRepository<Cv,Long> {
    @Query("SELECT SUM(c.views) FROM Cv c")
    Long sumViews();

    @Query("SELECT SUM(c.downloads) FROM Cv c")
    Long sumDownloads();
}
