package com.example.resumeScreening.Be.service;

import com.example.resumeScreening.Be.dto.job.JobResponseDTO;

import java.util.List;

public interface SavedJobService {

    String saveJob(Long jobId);

    String unsaveJob(Long jobId);

    List<JobResponseDTO> getSavedJobs();

}
