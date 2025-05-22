package com.example.resumeScreening.Be.service;

import com.example.resumeScreening.Be.dto.Application.ApplicationDTO;
import com.example.resumeScreening.Be.entity.Application;
import com.example.resumeScreening.Be.entity.Job;
import com.example.resumeScreening.Be.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ApplicationService {


    //apply
    String apply(Long jobId, MultipartFile file);


    //applications
    List<ApplicationDTO> getApplicationsForJob(Long jobId);


    //get pdf
    Application getById(Long id);


}
