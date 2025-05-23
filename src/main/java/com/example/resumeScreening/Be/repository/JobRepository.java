package com.example.resumeScreening.Be.repository;

import com.example.resumeScreening.Be.entity.Job;
import com.example.resumeScreening.Be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<Job,Long> {

  List<Job> findByPostedBy (User user);

  List<Job> findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(String titleKeyword, String categoryKeyword);



}
