package com.example.resumeScreening.Be.service.impl;

import com.example.resumeScreening.Be.dto.job.JobResponseDTO;
import com.example.resumeScreening.Be.entity.Job;
import com.example.resumeScreening.Be.entity.SavedJob;
import com.example.resumeScreening.Be.entity.User;
import com.example.resumeScreening.Be.exception.ResourceNotFoundException;
import com.example.resumeScreening.Be.mapper.JobMapper;
import com.example.resumeScreening.Be.repository.JobRepository;
import com.example.resumeScreening.Be.repository.SavedJobRepository;
import com.example.resumeScreening.Be.repository.UserRepository;
import com.example.resumeScreening.Be.service.SavedJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class SavedJobServiceImpl implements SavedJobService {

    @Autowired
    private SavedJobRepository savedJobRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;


    @Override
    public String saveJob(Long jobId) {
        Job job = jobRepository.findById(jobId).orElseThrow(()-> new ResourceNotFoundException("Job not found"));
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail).orElseThrow(()->new ResourceNotFoundException("User Not " +
                "Found"));
        SavedJob savedJob = new SavedJob();
        savedJob.setUser(user);
        savedJob.setJob(job);
        savedJobRepository.save(savedJob);
        return "Saved successfully";
    }

    @Override
    public String unsaveJob(Long jobId) {
        Job job = jobRepository.findById(jobId).orElseThrow(()-> new ResourceNotFoundException("Job not found"));
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail).orElseThrow(()->new ResourceNotFoundException("User Not " +
                "Found"));
        SavedJob savedJob =
                savedJobRepository.findByJobIdAndUserId(jobId,user.getId())
                        .orElseThrow(()-> new ResourceNotFoundException("Job id or User id Not match"));
        savedJobRepository.delete(savedJob);
        return "Unsaved successfully";
    }

    @Override
    public List<JobResponseDTO> getSavedJobs() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail).orElseThrow(()->new ResourceNotFoundException("User Not " +
                "Found"));
        List<SavedJob> savedJobs = savedJobRepository.findAllByUserId(user.getId());
        return savedJobs.stream()
                .map(SavedJob::getJob)
                .filter(Job::isActive)
                .filter(job -> !job.isDeleted())
                .map(JobMapper::mapToJobResponseDTO)
                .collect(Collectors.toList());
    }
}
