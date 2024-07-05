package com.esprit.jobfinder.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.esprit.jobfinder.models.enums.ERole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "users", 
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "username"),
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

  @ManyToMany
  Set<Skill> skills;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<UserQuiz> userQuizzes = new HashSet<>();

  @ManyToMany
  Set<Offer> offres;

  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime lastLogin;
  public User() {
  }
  @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
  private VerificationToken verificationToken;
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
