package com.example.resumeScreening.Be.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "jobs")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Job {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Column(name = "company_name")
    private String companyName;

    @Lob
    private String description;

    private String location;

    private boolean isActive;

    private String experience;

    private String education;

    @Column(name = "no_of_post")
    private Integer numberOfPost;

    private String jobType;

    private LocalDate deadline;

    private String workMode;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private boolean isDeleted=false;



    private String category;




    @ManyToOne
    @JoinColumn(name = "posted_by", nullable = false)
    private User postedBy;




    @OneToMany(mappedBy = "job")
    private List<Application> applications;
}
