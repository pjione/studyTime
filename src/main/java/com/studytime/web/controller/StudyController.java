package com.studytime.web.controller;

import com.studytime.domain.study.service.StudyService;
import com.studytime.web.request.StudyAddRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;

    @PostMapping("/study")
    public void addStudy(@RequestBody @Validated StudyAddRequest studyAddRequest){
        studyService.addStudy(studyAddRequest);
    }
}
