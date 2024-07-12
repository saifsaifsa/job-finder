package com.esprit.jobfinder.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esprit.jobfinder.models.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> , JpaSpecificationExecutor<User> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
  Optional<User> findByEmailAndPassword(String email,String password);

  Optional<User> findByEmail(String email);
  @Query("SELECT u FROM User u WHERE u.lastLogin < :cutoffDate")
  List<User> findInactiveUsers(LocalDateTime cutoffDate);

  @Modifying
  @Transactional
  void deleteAllByIdIn(List<Long> ids);
  @Query("SELECT COUNT(u) FROM User u")
  long countAllUsers();

  @Query("SELECT COUNT(u) FROM User u WHERE u.active = true")
  long countActiveUsers();

  @Query("SELECT u.role, COUNT(u) FROM User u GROUP BY u.role")
  List<Object[]> countUsersByRole();
}
