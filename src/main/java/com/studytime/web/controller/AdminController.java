package com.studytime.web.controller;

import com.studytime.domain.enums.StudyStatus;
import com.studytime.domain.study.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController { //todo 테스트코드

    private final StudyRepository studyRepository;

    @PatchMapping("/study/approve/{studyId}")
    public void approveStudy(@PathVariable Long studyId){
        studyRepository.updateStudyStatus(StudyStatus.PROGRESSED, studyId);
    }

    @PatchMapping("/study/refuse/{studyId}")
    public void refuseStudy(@PathVariable Long studyId){
        studyRepository.updateStudyStatus(StudyStatus.REFUSED, studyId);
    }
}