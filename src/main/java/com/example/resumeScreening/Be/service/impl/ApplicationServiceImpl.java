package com.example.resumeScreening.Be.service.impl;

import com.example.resumeScreening.Be.dto.Application.ApplicationDTO;
import com.example.resumeScreening.Be.entity.Application;
import com.example.resumeScreening.Be.entity.Job;
import com.example.resumeScreening.Be.entity.User;
import com.example.resumeScreening.Be.exception.AccessDeniedException;
import com.example.resumeScreening.Be.exception.ResourceAlreadyExistException;
import com.example.resumeScreening.Be.exception.ResourceNotFoundException;
import com.example.resumeScreening.Be.mapper.ApplicationMapper;
import com.example.resumeScreening.Be.repository.ApplicationRepository;
import com.example.resumeScreening.Be.repository.JobRepository;
import com.example.resumeScreening.Be.repository.UserRepository;
import com.example.resumeScreening.Be.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl implements ApplicationService{


    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private JobRepository jobRepository;



    @Override
    public Application apply(Long jobId, MultipartFile file) {

        // Get currently authenticated user
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        if (userEmail == null) {
            throw new ResourceNotFoundException("User not found");
        }

        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        // Check if already applied
        if (applicationRepository.existsByJobIdAndUserId(jobId, currentUser.getId())) {
            throw new ResourceAlreadyExistException("You have already applied to this job");
        }

        try {
            Application application = new Application();
            application.setFileName(file.getOriginalFilename());
            application.setFileType(file.getContentType());
            application.setFileData(file.getBytes());
            application.setJob(job);
            application.setUser(currentUser);

            return applicationRepository.save(application);
        } catch (IOException e) {
            throw new RuntimeException("Failed to process uploaded file", e);
        }

    }

    @Override
    public List<ApplicationDTO> getApplicationsForJob(Long jobId) {
        String hrEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        User hrUser = userRepository.findByEmail(hrEmail)
                .orElseThrow(() -> new ResourceNotFoundException("HR not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        // Ensure the logged-in HR is the one who posted the job
        if (!job.getPostedBy().getId().equals(hrUser.getId())) {
            throw new AccessDeniedException("You are not authorized to view applications for this job");
        }

        List<Application> applications = applicationRepository.findByJobId(jobId);

        return applications.stream()
                .map(ApplicationMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Application getById(Long id) {
        return applicationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Application not " +
                "found"));
    }


}



