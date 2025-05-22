package com.example.resumeScreening.Be.dto.Application;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApplicationDTO {

    private Long id;
    private String fileName;
    private String fileType;
    private LocalDateTime appliedAt;
    private String userName;
    private String userEmail;
    private String jobTitle;
}
