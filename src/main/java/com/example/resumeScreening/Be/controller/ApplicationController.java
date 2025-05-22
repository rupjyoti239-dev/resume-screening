package com.example.resumeScreening.Be.controller;

import com.example.resumeScreening.Be.entity.Application;
import com.example.resumeScreening.Be.entity.Job;
import com.example.resumeScreening.Be.entity.User;
import com.example.resumeScreening.Be.repository.JobRepository;
import com.example.resumeScreening.Be.response.ApiResponse;
import com.example.resumeScreening.Be.service.ApplicationService;
import com.example.resumeScreening.Be.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/user/applications")
public class ApplicationController {


    @Autowired
    private ApplicationService applicationService;




    @PostMapping("/apply/{jobId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse<Application>> applyToJob(
            @PathVariable Long jobId,
            @RequestParam("file") MultipartFile file) {

        Application application = applicationService.apply(jobId, file);

        ApiResponse<Application> response = new ApiResponse<>(
                true,
                application,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
