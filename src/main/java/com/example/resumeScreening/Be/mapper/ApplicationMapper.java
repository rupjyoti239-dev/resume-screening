package com.example.resumeScreening.Be.mapper;

import com.example.resumeScreening.Be.dto.Application.ApplicationDTO;
import com.example.resumeScreening.Be.entity.Application;

public class ApplicationMapper {
    public static ApplicationDTO convertToDto(Application app) {
        ApplicationDTO dto = new ApplicationDTO();
        dto.setId(app.getId());
        dto.setFileName(app.getFileName());
        dto.setFileType(app.getFileType());
        dto.setAppliedAt(app.getAppliedAt());
        dto.setUserName(app.getUser().getFirstName() + " " + app.getUser().getLastName());
        dto.setUserEmail(app.getUser().getEmail());
        dto.setJobTitle(app.getJob().getTitle());
        return dto;
    }

}
