package com.esprit.jobfinder.models;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.esprit.jobfinder.models.enums.ERole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users", 
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "username"),
      @UniqueConstraint(columnNames = "email") 
    })
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Username cannot be blank")
  private String username;
  private String firstName;

  private String lastName;
  @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
  private Boolean active = false;
  @Email(message = "Invalid email format")
  private String email;
  @NotBlank(message = "password cannot be blank")
  @Size(max = 120)
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "Password must have at least one lowercase letter, one uppercase letter, and one digit, and its length should be at least 8 characters")
  private String password;

  @NotBlank(message = "phone cannot be blank")
  @Pattern(regexp = "^\\+216(20|21|22|23|24|25|26|27|28|29|50|52|53|54|55|56|58|90|91|92|93|94|95|96|97|98|99)\\d{6}$", message = "Phone number must be a valid Tunisian phone number")
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
  @ManyToMany
  Set<Quiz> quizs;

  @ManyToMany
  Set<Offer> offres;
  public User() {
  }
  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public ERole getRole() {
    return role;
  }

  public void setRole(ERole role) {
    this.role = role;
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

  public Set<Quiz> getQuizs() {
    return quizs;
  }

  public void setQuizs(Set<Quiz> quizs) {
    this.quizs = quizs;
  }

  public Set<Offer> getOffres() {
    return offres;
  }

  public void setOffres(Set<Offer> offres) {
    this.offres = offres;
  }
}
