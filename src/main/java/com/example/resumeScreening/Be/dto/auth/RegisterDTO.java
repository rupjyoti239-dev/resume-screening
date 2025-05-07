package com.example.resumeScreening.Be.dto.auth;

import com.example.resumeScreening.Be.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDTO {

    @NotBlank(message = "First name cannot be blank")
    @Size(min = 3, max = 10, message = "First name must be between 3 and 10 " +
            "characters")
    private String firstName;


    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 3, max = 10, message = "Last name must be between 3 and 10 " +
            "characters")
    private String lastName;


    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;


    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;


    @NotBlank(message = "Contact number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Contact must be a valid 10-digit Indian mobile number")
    private String contact;

    private Role role;

}
