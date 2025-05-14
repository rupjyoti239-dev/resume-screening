package com.example.resumeScreening.Be.dto.job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobRequestDTO {

    private String title;
    private String companyName;
    private String description;
    private String location;
    private boolean isActive;
    private String experience;
    private String education;
    private Integer numberOfPost;
    private String jobType;
    private LocalDate deadline;
    private String workMode;
    private String category;

}
