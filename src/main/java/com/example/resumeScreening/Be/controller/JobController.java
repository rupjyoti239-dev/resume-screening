package com.example.resumeScreening.Be.controller;


import com.example.resumeScreening.Be.dto.Application.ApplicationDTO;
import com.example.resumeScreening.Be.dto.job.JobRequestDTO;
import com.example.resumeScreening.Be.dto.job.JobResponseDTO;
import com.example.resumeScreening.Be.entity.Application;
import com.example.resumeScreening.Be.response.ApiResponse;
import com.example.resumeScreening.Be.service.ApplicationService;
import com.example.resumeScreening.Be.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/jobs")
public class JobController {


    @Autowired
    private JobService jobService;



    // add job
    @PostMapping
    @PreAuthorize("hasRole('ROLE_HR')")
    public ResponseEntity<ApiResponse<JobResponseDTO>> createJob(@RequestBody JobRequestDTO jobRequestDTO) {
       JobResponseDTO data = jobService.addJob(jobRequestDTO);
       ApiResponse<JobResponseDTO> response = new ApiResponse<>(
               true,
               data,
               LocalDateTime.now()
       );
       return new ResponseEntity<>(response,HttpStatus.CREATED);
    }


    // update job
    @PatchMapping("/{jobId}")
    @PreAuthorize("hasRole('ROLE_HR')")
    public ResponseEntity<ApiResponse<JobResponseDTO>> updateJob(@RequestBody JobRequestDTO jobRequestDTO,
                                                                 @PathVariable Long jobId){
        JobResponseDTO dto = jobService.updateJob(jobRequestDTO,jobId);
        ApiResponse<JobResponseDTO> response = new ApiResponse<>(
                true,
                dto,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    //get my job (posted by logged in hr)
    @GetMapping("/my-jobs")
    @PreAuthorize("hasRole('ROLE_HR')")
    public ResponseEntity<ApiResponse<List<JobResponseDTO>>> getMyJobs() {
        List<JobResponseDTO> myJobs = jobService.getMyJob();
        ApiResponse<List<JobResponseDTO>> response = new ApiResponse<>(
                true,
                myJobs,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    // 3. Get all jobs (Accessible by user, admin, hr)
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_HR')")
    public ResponseEntity<ApiResponse<List<JobResponseDTO>>> getAllJobs() {
        List<JobResponseDTO> allJobs = jobService.getAll();
        ApiResponse<List<JobResponseDTO>> response = new ApiResponse<>(
                true,
                allJobs,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    // change status
    @PatchMapping("/{jobId}/status")
    @PreAuthorize("hasRole('ROLE_HR')")
    public ResponseEntity<ApiResponse<?>> changeJobStatus(@PathVariable Long jobId) {
        String status = jobService.changeStatus(jobId);
        ApiResponse<?> response = new ApiResponse<>(
                true,
                status,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);

    }



    //delete job
    @DeleteMapping("/{jobId}")
    @PreAuthorize("hasRole('ROLE_HR')")
    public ResponseEntity<ApiResponse<?>> deleteJob(@PathVariable Long jobId) {
        String delete = jobService.deleteJob(jobId);
        ApiResponse<?> response = new ApiResponse<>(
                true,
                delete,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);

    }


    //search
    @GetMapping("/search")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<JobResponseDTO>> searchJobs(@RequestParam String keyword) {
        List<JobResponseDTO> jobs = jobService.searchJobsByTitle(keyword);
        return ResponseEntity.ok(jobs);
    }











}
