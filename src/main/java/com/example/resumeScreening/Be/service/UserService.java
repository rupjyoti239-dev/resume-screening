package com.example.resumeScreening.Be.service;

import com.example.resumeScreening.Be.dto.password.ResetPasswordDTO;

public interface UserService {


    //password reset
    String resetPassword(ResetPasswordDTO resetPasswordDTO);
}
