package com.esprit.jobfinder.payload.request;

import com.esprit.jobfinder.models.enums.ERole;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PatchUserRequest {

    private String username;

    private String firstName;

    private String lastName;

    private Boolean active;

    private String email;

    private String password;

    private String phone;

    private ERole role;
}
