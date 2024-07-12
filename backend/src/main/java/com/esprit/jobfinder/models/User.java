package com.esprit.jobfinder.models;

import com.esprit.jobfinder.models.enums.ERole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "users", 
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "username"),
      @UniqueConstraint(columnNames = "phone"),
      @UniqueConstraint(columnNames = "email") 
    })
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String firstName;
  private String lastName;
  @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
  private Boolean active = false;
  private String email;
  private String password;

  private String phone;

  @Enumerated(EnumType.ORDINAL)
  private ERole role;

  @ManyToMany
  Set<Training> trainings;

  @Temporal(TemporalType.DATE)
  private LocalDate birthDay;

  private String profilePicture;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
          name = "user_skills",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "competence_id")
  )
  private List<Competence> skills;

  @JsonIgnore
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<UserQuiz> userQuizzes = new HashSet<>();

  @ManyToMany
  Set<Offer> offres;

  @ManyToMany
  Set<Company> companies;

  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime lastLogin;

  @JsonIgnore
  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private VerificationToken verificationToken;
  public User() {
  }
  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }
  public User(String firstName,String lastName,String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.lastName = lastName;
    this.firstName = firstName;
  }

  public String getFullName(){
    return firstName+" "+lastName;
  }
}
