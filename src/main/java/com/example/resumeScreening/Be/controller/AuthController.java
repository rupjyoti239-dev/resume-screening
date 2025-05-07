package com.example.resumeScreening.Be.controller;

import com.example.resumeScreening.Be.dto.auth.OtpDTO;
import com.example.resumeScreening.Be.dto.auth.RegisterDTO;
import com.example.resumeScreening.Be.response.ApiResponse;
import com.example.resumeScreening.Be.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    //register
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@Valid @RequestBody  RegisterDTO registerDTO){
        String message = authService.registerUser(registerDTO);
        ApiResponse<?> response = new ApiResponse<>(
                true,
                message,
                null,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }


    //verify otp
    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<?>> verifyOtp(@Valid @RequestBody OtpDTO otpDTO){
        String message = authService.verifyOtp(otpDTO);
        ApiResponse<?> response = new ApiResponse<>(
                true,
                message,
                null,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
}
