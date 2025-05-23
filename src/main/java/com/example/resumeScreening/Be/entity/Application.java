package com.example.resumeScreening.Be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "applications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String fileName;
    private String fileType;

//    private Double atsScore;


    @Lob
    private byte[] fileData;

    @CreationTimestamp
    private LocalDateTime appliedAt;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    @JsonIgnore
    private Job job;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
}
