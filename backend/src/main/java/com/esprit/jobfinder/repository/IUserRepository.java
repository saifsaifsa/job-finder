package com.esprit.jobfinder.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.esprit.jobfinder.models.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> , JpaSpecificationExecutor<User> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
  Optional<User> findByEmailAndPassword(String email,String password);

  Optional<User> findByEmail(String email);
}
