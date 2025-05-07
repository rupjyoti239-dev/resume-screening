package com.example.resumeScreening.Be.service.impl;

import com.example.resumeScreening.Be.dto.auth.LoginDTO;
import com.example.resumeScreening.Be.dto.auth.LoginResponseDTO;
import com.example.resumeScreening.Be.dto.auth.OtpDTO;
import com.example.resumeScreening.Be.dto.auth.RegisterDTO;
import com.example.resumeScreening.Be.entity.User;
import com.example.resumeScreening.Be.exception.InValidOtpException;
import com.example.resumeScreening.Be.exception.ItemAlreadyExistException;
import com.example.resumeScreening.Be.exception.ItemNotFoundException;
import com.example.resumeScreening.Be.mapper.UserMapper;
import com.example.resumeScreening.Be.repository.UserRepository;
import com.example.resumeScreening.Be.service.AuthService;
import com.example.resumeScreening.Be.service.EmailService;
import com.example.resumeScreening.Be.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtService jwtService;





    @Override
    public String registerUser(RegisterDTO registerDTO) {
        Optional<User> existingUserOpt = userRepository.findByEmail(registerDTO.getEmail());

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            if (existingUser.getIsVerified()) {
                throw new ItemAlreadyExistException("Email is already registered");
            }

            sendOtpToUser(existingUser);
            return "OTP resent to existing unverified user";
        }

        // New user
        User user = UserMapper.registerDtoToEntity(registerDTO);
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setIsActive(false);
        user.setIsVerified(false);

        sendOtpToUser(user);
        return "OTP has been sent";
    }



    private void sendOtpToUser(User user) {
        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);
        user.setOtp(otp);
        userRepository.save(user);

        String subject = "Your OTP for Account Verification";
        String body = "Dear " + user.getFirstName() + ", Your OTP is: " + otp;
        emailService.sendOtp(user.getEmail(), subject, body);
    }




    @Override
    public String verifyOtp(OtpDTO otpDTO) {
        User user = userRepository.findByEmail(otpDTO.getEmail())
                .orElseThrow(() -> new ItemNotFoundException("User not found with email: " + otpDTO.getEmail()));


        if(user.getIsVerified()){
            throw new ItemAlreadyExistException("User already registered");
        }

        if (user.getOtp() == null || !user.getOtp().equals(otpDTO.getOtp())) {
            throw new InValidOtpException("Invalid or expired OTP");
        }

        user.setIsVerified(true);
        user.setIsActive(true);
        user.setOtp(null);
        userRepository.save(user);

        return "OTP verified successfully. Account activated.";

    }

    @Override
    public LoginResponseDTO login(LoginDTO loginDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );
        var user =
                userRepository.findByEmail(loginDTO.getEmail())
                        .orElseThrow(()->new ItemNotFoundException("user not found"));
        String token = jwtService.generateToken(user);
        return LoginResponseDTO.builder().accessToken(token).build();

    }


}
