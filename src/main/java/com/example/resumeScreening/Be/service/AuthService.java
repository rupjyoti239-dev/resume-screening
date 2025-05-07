package com.example.resumeScreening.Be.service;

import com.example.resumeScreening.Be.dto.auth.OtpDTO;
import com.example.resumeScreening.Be.dto.auth.RegisterDTO;

public interface AuthService {

    //register
    String registerUser(RegisterDTO registerDTO);

    //verify otp
    String verifyOtp(OtpDTO otpDTO);

}
