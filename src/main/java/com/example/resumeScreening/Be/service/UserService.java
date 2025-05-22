package com.example.resumeScreening.Be.service;

import com.example.resumeScreening.Be.dto.job.JobResponseDTO;
import com.example.resumeScreening.Be.dto.password.ResetPasswordDTO;

import java.util.List;

public interface UserService {


    //password reset
    String resetPassword(ResetPasswordDTO resetPasswordDTO);



}
