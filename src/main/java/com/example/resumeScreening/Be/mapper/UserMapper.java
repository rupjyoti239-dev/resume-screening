package com.example.resumeScreening.Be.mapper;

import com.example.resumeScreening.Be.dto.auth.RegisterDTO;
import com.example.resumeScreening.Be.entity.User;

public class UserMapper {

    public static User registerDtoToEntity(RegisterDTO registerDTO){
        User user = new User();
        user.setFirstName(registerDTO.getFirstName());
        user.setLastName(registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        user.setContact(registerDTO.getContact());
        user.setRole(registerDTO.getRole());
        return user;
    }
}
