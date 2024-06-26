package com.esprit.jobfinder.payload.request;

import java.util.Set;

import com.esprit.jobfinder.models.enums.ERole;
import jakarta.validation.constraints.*;

public class SignupRequest {
  @NotBlank(message = "Username cannot be blank")
  @Size(max = 20, message = "Username must be less than or equal to 20 characters")
  private String username;

  @NotBlank(message = "Email cannot be blank")
  @Size(max = 50)
  @Email(message = "Invalid email format")
  private String email;

  private ERole role;

  @NotBlank(message = "Password cannot be blank")
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "Password must have at least one lowercase letter, one uppercase letter, and one digit, and its length should be at least 8 characters")
  private String password;

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

  public ERole getRole() {
    return role;
  }

  public void setRole(ERole role) {
    this.role = role;
  }
}
