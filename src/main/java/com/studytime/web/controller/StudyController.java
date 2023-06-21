package com.studytime.web.controller;

import com.studytime.domain.study.service.StudyService;
import com.studytime.web.request.StudyAddRequest;
import com.studytime.web.request.StudySearchRequest;
import com.studytime.web.response.ListResult;
import com.studytime.web.response.StudyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/study/{studyId}")
    public StudyResponse getStudy(@PathVariable Long studyId){ //todo 테스트코드
        return studyService.getStudy(studyId);
    }

   /* @PostMapping("/study/{studyId}")
    public void joinStudy(@PathVariable Long studyId){
        studyService.joinStudy(studyId);
    }*/
}
