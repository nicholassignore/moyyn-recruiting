package com.moyyn.recruiting.repository;

import com.moyyn.recruiting.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
}
