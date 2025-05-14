package com.example.resumeScreening.Be.dto.password;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPasswordDTO {
    private String currentPassword;

    @Size(min = 6, max = 10, message = "Password must be between 6-10 character")
    private String newPassword;
    private String confirmPassword;
}
