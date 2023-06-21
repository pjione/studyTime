package com.studytime.domain.study.service;

import com.studytime.web.request.StudyAddRequest;
import com.studytime.web.request.StudySearchRequest;
import com.studytime.web.response.StudyResponse;

import java.util.List;

public interface StudyService {
    void addStudy(StudyAddRequest studyAddRequest);
    List<StudyResponse> studyList(StudySearchRequest studySearchRequest);
    StudyResponse getStudy(Long studyId);
}
