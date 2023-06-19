package com.studytime.domain.study.repository;

import com.studytime.domain.study.Study;
import com.studytime.web.request.StudySearchRequest;

import java.awt.print.Pageable;
import java.util.List;

public interface StudyQRepository{
    List<Study> getList(StudySearchRequest studySearchRequest);
}
