package com.example.resumeScreening.Be.repository;

import com.example.resumeScreening.Be.entity.SavedJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SavedJobRepository extends JpaRepository<SavedJob,Long> {
   Optional<SavedJob> findByJobIdAndUserId(Long jobId, Long userId);
   List<SavedJob> findAllByUserId(Long userId);
}
