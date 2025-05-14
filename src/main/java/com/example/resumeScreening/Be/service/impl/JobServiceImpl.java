package com.example.resumeScreening.Be.service.impl;

import com.example.resumeScreening.Be.dto.job.JobRequestDTO;
import com.example.resumeScreening.Be.dto.job.JobResponseDTO;
import com.example.resumeScreening.Be.entity.Category;
import com.example.resumeScreening.Be.entity.Job;
import com.example.resumeScreening.Be.entity.User;
import com.example.resumeScreening.Be.exception.AccessDeniedException;
import com.example.resumeScreening.Be.exception.ResourceNotFoundException;
import com.example.resumeScreening.Be.mapper.JobMapper;
import com.example.resumeScreening.Be.repository.CategoryRepository;
import com.example.resumeScreening.Be.repository.JobRepository;
import com.example.resumeScreening.Be.repository.UserRepository;
import com.example.resumeScreening.Be.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private JobRepository jobRepository;


    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public JobResponseDTO addJob(JobRequestDTO jobRequestDTO) {
        String hrEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        User hrUser = userRepository.findByEmail(hrEmail)
                .orElseThrow(() -> new ResourceNotFoundException("HR user not found"));


        Job job = JobMapper.mapToJob(jobRequestDTO, hrUser);
        job.setActive(true);

        job = jobRepository.save(job);

        return JobMapper.mapToJobResponseDTO(job);
    }


    @Override
    public JobResponseDTO updateJob(JobRequestDTO request, Long jobId) {
        String hrEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        User hr = userRepository.findByEmail(hrEmail)
                .orElseThrow(() -> new ResourceNotFoundException("HR not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        if (!job.getPostedBy().getId().equals(hr.getId())) {
            throw new AccessDeniedException("You are not authorized to update this job");
        }

        if (request.getTitle() != null) job.setTitle(request.getTitle());
        if (request.getDescription() != null) job.setDescription(request.getDescription());
        if (request.getCompanyName() != null) job.setCompanyName(request.getCompanyName());
        if (request.getLocation() != null) job.setLocation(request.getLocation());
        if (request.getExperience() != null) job.setExperience(request.getExperience());
        if (request.getEducation() != null) job.setEducation(request.getEducation());
        if (request.getNumberOfPost() != null) job.setNumberOfPost(request.getNumberOfPost());
        if (request.getJobType() != null) job.setJobType(request.getJobType());
        if (request.getDeadline() != null) job.setDeadline(request.getDeadline());
        if (request.getWorkMode() != null) job.setWorkMode(request.getWorkMode());
        if (request.getCategory() != null) job.setCategory(request.getCategory());

        Job updatedJob = jobRepository.save(job);
        return JobMapper.mapToJobResponseDTO(updatedJob);
    }


    @Override
    public List<JobResponseDTO> getMyJob() {
      String hrEmail =
              SecurityContextHolder.getContext().getAuthentication().getName();
      User hr = userRepository.findByEmail(hrEmail).orElseThrow(
              ()-> new ResourceNotFoundException("HR Not Found")
      );

      List<Job> jobs = jobRepository.findByPostedBy(hr);
      return jobs.stream().map(JobMapper::mapToJobResponseDTO).collect(Collectors.toList());

    }

    @Override
    public List<JobResponseDTO> getAll() {
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream()
                .filter(Job::isActive)
                .map(JobMapper::mapToJobResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public String changeStatus(Long jobId) {
        String hrEmail =
                SecurityContextHolder.getContext().getAuthentication().getName();
        User hr = userRepository.findByEmail(hrEmail).orElseThrow(
                ()-> new ResourceNotFoundException("HR not found")
        );

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        if (!job.getPostedBy().getId().equals(hr.getId())) {
            throw new AccessDeniedException("You are not authorized to update this job");
        }

        job.setActive(!job.isActive());

        Job updatedJob = jobRepository.save(job);

        return "Status updated successfully";

    }

}
