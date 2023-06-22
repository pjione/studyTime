package com.studytime.domain.study.repository;

import com.studytime.domain.studyuser.StudyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudyUserRepository extends JpaRepository<StudyUser, Long> {

    Optional<StudyUser> findByStudyIdAndUserId(Long StudyId, Long UserId);
}
