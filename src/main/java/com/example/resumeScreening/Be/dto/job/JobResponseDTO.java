package com.example.resumeScreening.Be.dto.job;

import com.example.resumeScreening.Be.entity.Category;
import com.example.resumeScreening.Be.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobResponseDTO {


    private Long id;
    private String title;
    private String companyName;
    private String description;
    private String location;
    private boolean isActive;
    private String experience;
    private String education;
    private Integer numberOfPost;
    private String jobType;
    private LocalDate deadLine;
    private String WorkMode;
    private String  postedByEmail;
    private String category;


}
