package com.studytime.domain.study.repository;

import com.studytime.domain.studyuser.StudyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyUserRepository extends JpaRepository<StudyUser, Long> {
}
