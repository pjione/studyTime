package com.studytime.domain.study.repository;

import com.studytime.domain.study.Study;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudyRepository extends JpaRepository<Study, Long>, StudyQRepository {

    @EntityGraph(attributePaths = {"user"})
    @Query("select s from Study s")
    Optional<Study> whenModifyFindById(Long studyId);
}
