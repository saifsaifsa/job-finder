package com.esprit.jobfinder.repository;

import com.esprit.jobfinder.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IVerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    @Query("SELECT vt FROM VerificationToken vt WHERE vt.token = :token AND vt.user.id = :userId")
    VerificationToken findByTokenAndUserId(@Param("token") String token,@Param("userId") Long userId);
    VerificationToken findByToken(String token);
}
