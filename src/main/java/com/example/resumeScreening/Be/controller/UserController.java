package com.example.resumeScreening.Be.controller;


import com.example.resumeScreening.Be.dto.password.ResetPasswordDTO;
import com.example.resumeScreening.Be.response.ApiResponse;
import com.example.resumeScreening.Be.service.JobService;
import com.example.resumeScreening.Be.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {



    @Autowired
    private UserService userService;




    //reset password
    @PostMapping("/reset-password")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_HR')")
    public ResponseEntity<ApiResponse<String>> passwordReset(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO){
        String data = userService.resetPassword(resetPasswordDTO);
        ApiResponse<String> response  = new ApiResponse<>(
                true,
                data,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }








}
