package com.studytime.domain.study.repository;

import com.studytime.domain.study.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
