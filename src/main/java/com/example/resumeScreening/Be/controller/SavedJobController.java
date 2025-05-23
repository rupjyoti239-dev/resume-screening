package com.example.resumeScreening.Be.controller;

import com.example.resumeScreening.Be.dto.job.JobResponseDTO;
import com.example.resumeScreening.Be.response.ApiResponse;
import com.example.resumeScreening.Be.service.SavedJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequestMapping("/api/save-job")
public class SavedJobController {

    @Autowired
    private SavedJobService savedJobService;

    @PostMapping("/{jobId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse<String>> saveJob(@PathVariable Long jobId) {
        String saveedJob = savedJobService.saveJob(jobId);
        ApiResponse<String> response = new ApiResponse<>(
                true,
                null,
                LocalDateTime.now()
        );
       return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{jobId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse<String>> unsaveJob(@PathVariable Long jobId) {
       String savedJob = savedJobService.unsaveJob(jobId);
        ApiResponse<String> response = new ApiResponse<>(
                true,
                null,
                LocalDateTime.now()
        );
        return  new ResponseEntity<>(response,HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse<List<JobResponseDTO>>> getSavedJobs() {
        List<JobResponseDTO> savedJobs = savedJobService.getSavedJobs();
        ApiResponse<List<JobResponseDTO>> response = new ApiResponse<>(
                true,
                savedJobs,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
