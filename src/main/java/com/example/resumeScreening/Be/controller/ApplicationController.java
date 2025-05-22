package com.example.resumeScreening.Be.controller;

import com.example.resumeScreening.Be.dto.Application.ApplicationDTO;
import com.example.resumeScreening.Be.entity.Application;
import com.example.resumeScreening.Be.response.ApiResponse;
import com.example.resumeScreening.Be.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {


    @Autowired
    private ApplicationService applicationService;




    @PostMapping("/{jobId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse<String>> applyToJob(
            @PathVariable Long jobId,
            @RequestParam("file") MultipartFile file) {

       String application = applicationService.apply(jobId, file);

        ApiResponse<String> response = new ApiResponse<>(
                true,
                null,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }




    //get applications
    @GetMapping("/{jobId}")
    @PreAuthorize("hasRole('ROLE_HR')")
    public ResponseEntity<ApiResponse<List<ApplicationDTO>>> getApplicationsForJob(@PathVariable Long jobId) {
        List<ApplicationDTO> applications = applicationService.getApplicationsForJob(jobId);

        ApiResponse<List<ApplicationDTO>> response = new ApiResponse<>(
                true,
                applications,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{applicationId}/resume")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<?> getResumeFile(@PathVariable Long applicationId) {
        Application application = applicationService.getById(applicationId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(application.getFileType()));
        headers.setContentDispositionFormData("inline", application.getFileName());

        return new ResponseEntity<>(application.getFileData(), headers, HttpStatus.OK);
    }




}
