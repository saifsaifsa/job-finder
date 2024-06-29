package com.esprit.jobfinder.payload.request;

import com.esprit.jobfinder.models.enums.ERole;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class UpdateUserReq {
    @NotBlank(message = "Username cannot be blank")
    private String username;
    private String firstName;

    private String lastName;
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean active = false;
    @Email(message = "Invalid email format")
    private String email;

//    @NotBlank(message = "password cannot be blank")
//    @Size(max = 120)
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "Password must have at least one lowercase letter, one uppercase letter, and one digit, and its length should be at least 8 characters")
//    private String password;

    @NotBlank(message = "phone cannot be blank")
    @Pattern(regexp = "^\\+216(20|21|22|23|24|25|26|27|28|29|50|52|53|54|55|56|58|90|91|92|93|94|95|96|97|98|99)\\d{6}$", message = "Phone number must be a valid Tunisian phone number")
    private String phone;

    private String role;
    private MultipartFile photo;
    private String birthDay;
    public String getFullName(){
        return firstName+" "+lastName;
    }
}
