package com.esprit.jobfinder.models;

import com.esprit.jobfinder.models.enums.ERole;
import jakarta.persistence.*;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
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

  @ManyToMany
  Set<Skill> skills;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<UserQuiz> userQuizzes = new HashSet<>();
  @JsonIgnore
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

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public LocalDate getBirthDay() {
    return birthDay;
  }

  public void setBirthDay(LocalDate birthDay) {
    this.birthDay = birthDay;
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", active=" + active +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", phone='" + phone + '\'' +
            ", role=" + role +
            ", birthDay=" + birthDay +
            '}';
  }

  public String getProfilePicture() {
    return profilePicture;
  }

  public void setProfilePicture(String profilePicture) {
    this.profilePicture = profilePicture;
  }

  public Set<Training> getTrainings() {
    return trainings;
  }

  public void setTrainings(Set<Training> trainings) {
    this.trainings = trainings;
  }

  public Set<Skill> getSkills() {
    return skills;
  }

  public void setSkills(Set<Skill> skills) {
    this.skills = skills;
  }



  public Set<Offer> getOffres() {
    return offres;
  }

  public void setOffres(Set<Offer> offres) {
    this.offres = offres;
  }

  public Set<Company> getCompanies() {
    return companies;
  }

  public void setCompanies(Set<Company> companies) {
    this.companies = companies;
  }

  public LocalDateTime getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(LocalDateTime lastLogin) {
    this.lastLogin = lastLogin;
  }
}
