package com.example.resumeScreening.Be.repository;

import com.example.resumeScreening.Be.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application,Long> {
    List<Application> findByJobId(Long jobId);
    List<Application> findByUserId(Long userId);
    boolean existsByJobIdAndUserId(Long jobId, Long userId);
}
