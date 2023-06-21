package com.studytime.web.controller;

import com.studytime.domain.study.service.StudyService;
import com.studytime.web.request.StudyAddRequest;
import com.studytime.web.request.StudySearchRequest;
import com.studytime.web.response.ListResult;
import com.studytime.web.response.StudyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;

    @PostMapping("/study")
    public void addStudy(@RequestBody @Validated StudyAddRequest studyAddRequest){
        studyService.addStudy(studyAddRequest);
    }

    @GetMapping("/study")
    public ListResult<List<StudyResponse>> studyList(@RequestBody StudySearchRequest studySearchRequest){
        List<StudyResponse> studyList = studyService.studyList(studySearchRequest);
        return new ListResult<>(studyList);
    }
}
