package com.example.resumeScreening.Be.service.impl;


import com.example.resumeScreening.Be.dto.password.ResetPasswordDTO;
import com.example.resumeScreening.Be.entity.User;
import com.example.resumeScreening.Be.exception.InValidDataException;
import com.example.resumeScreening.Be.exception.ResourceNotFoundException;
import com.example.resumeScreening.Be.repository.UserRepository;
import com.example.resumeScreening.Be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;




    @Override
    public String resetPassword(ResetPasswordDTO resetPasswordDTO) {
        String currentPassword = resetPasswordDTO.getCurrentPassword();
        String newPassword = resetPasswordDTO.getNewPassword();
        String confirmPassword = resetPasswordDTO.getConfirmPassword();

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User not found"));

        if(!passwordEncoder.matches(currentPassword,user.getPassword())){
            throw new BadCredentialsException("Current Password does not match");
        }

        if(!newPassword.equals(confirmPassword)){
            throw new InValidDataException("New password and confirm password does not match");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return "password saved";
    }
}
