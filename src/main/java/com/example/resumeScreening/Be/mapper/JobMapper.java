package com.example.resumeScreening.Be.mapper;

import com.example.resumeScreening.Be.dto.job.JobRequestDTO;
import com.example.resumeScreening.Be.dto.job.JobResponseDTO;
import com.example.resumeScreening.Be.entity.Category;
import com.example.resumeScreening.Be.entity.Job;
import com.example.resumeScreening.Be.entity.User;
import com.example.resumeScreening.Be.exception.ResourceNotFoundException;
import com.example.resumeScreening.Be.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

public class JobMapper {


    @Autowired


    public static Job mapToJob(JobRequestDTO jobRequestDTO,
                               User postedBy) {
        Job job = new Job();
        job.setTitle(jobRequestDTO.getTitle());
        job.setCompanyName(jobRequestDTO.getCompanyName());
        job.setDescription(jobRequestDTO.getDescription());
        job.setLocation(jobRequestDTO.getLocation());
        job.setActive(jobRequestDTO.isActive());
        job.setExperience(jobRequestDTO.getExperience());
        job.setEducation(jobRequestDTO.getEducation());
        job.setNumberOfPost(jobRequestDTO.getNumberOfPost());
        job.setJobType(jobRequestDTO.getJobType());
        job.setDeadline(jobRequestDTO.getDeadline());
        job.setWorkMode(jobRequestDTO.getWorkMode());
        job.setCategory(jobRequestDTO.getCategory());
        job.setPostedBy(postedBy);
       return job;
    }

    public static JobResponseDTO mapToJobResponseDTO(Job job) {
        JobResponseDTO dto = new JobResponseDTO();
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setCompanyName(job.getCompanyName());
        dto.setDescription(job.getDescription());
        dto.setLocation(job.getLocation());
        dto.setActive(job.isActive());
        dto.setExperience(job.getExperience());
        dto.setEducation(job.getEducation());
        dto.setNumberOfPost(job.getNumberOfPost());
        dto.setJobType(job.getJobType());
        dto.setWorkMode(job.getWorkMode());
        dto.setDeadLine(job.getDeadline());
        dto.setPostedByEmail(job.getPostedBy().getEmail());
        dto.setCategory(job.getCategory());
        return dto;
    }

}
