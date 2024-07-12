package com.esprit.jobfinder.payload.request;

import com.esprit.jobfinder.models.Skill;
import com.esprit.jobfinder.models.Training;
import com.esprit.jobfinder.models.enums.ERole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
public class CreateUserReq {

    private MultipartFile photo;

    @NotBlank(message = "Username cannot be blank")
    private String username;

    @NotBlank(message = "firstName cannot be blank")
    private String firstName;

    @NotBlank(message = "lastName cannot be blank")
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

    private String role;

    private String birthDay;
    public String getFullName(){
        return firstName+" "+lastName;
    }
    private Set<Long> skills;
}
