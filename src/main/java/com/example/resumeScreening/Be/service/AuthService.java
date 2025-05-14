package com.example.resumeScreening.Be.service;

import com.example.resumeScreening.Be.dto.auth.LoginDTO;
import com.example.resumeScreening.Be.dto.auth.LoginResponseDTO;
import com.example.resumeScreening.Be.dto.auth.OtpDTO;
import com.example.resumeScreening.Be.dto.auth.RegisterDTO;
import com.example.resumeScreening.Be.dto.password.ResetPasswordDTO;

public interface AuthService {

    //register
    String registerUser(RegisterDTO registerDTO);

    //verify otp
    String verifyOtp(OtpDTO otpDTO);


    //login
    LoginResponseDTO login(LoginDTO loginDTO);




}
