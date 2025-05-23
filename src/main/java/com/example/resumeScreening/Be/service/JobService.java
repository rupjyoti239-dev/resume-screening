package com.example.resumeScreening.Be.service;

import com.example.resumeScreening.Be.dto.job.JobRequestDTO;
import com.example.resumeScreening.Be.dto.job.JobResponseDTO;
import com.example.resumeScreening.Be.entity.Application;
import com.example.resumeScreening.Be.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobService {


    //create
    JobResponseDTO addJob(JobRequestDTO jobRequestDTO);



    //update
    JobResponseDTO updateJob(JobRequestDTO jobRequestDTO,Long jobId);


    //get my job
    List<JobResponseDTO> getMyJob();


    //get all job
    List<JobResponseDTO> getAll();




    //change status
    String changeStatus(Long jobId);


    String deleteJob(Long jobId);

    List<JobResponseDTO> searchJobsByTitle(String keyword);







}
