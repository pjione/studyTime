package com.studytime.domain.study.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.studytime.domain.study.QStudy;
import com.studytime.domain.study.Study;
import com.studytime.web.request.StudySearchRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class StudyQRepositoryImpl implements StudyQRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Study> getList(StudySearchRequest studySearchRequest) {

        if(studySearchRequest.getOption().equals("title")){
            return searchByTitle(studySearchRequest);
        }
        return searchByTitleOrContent(studySearchRequest);
    }

    private List<Study> searchByTitleOrContent(StudySearchRequest studySearchRequest) {
        return jpaQueryFactory.selectFrom(QStudy.study)
                .limit(studySearchRequest.getSize())
                .offset(studySearchRequest.getOffset())
                .orderBy(QStudy.study.id.desc())
                .where(QStudy.study.title.contains(studySearchRequest.getKeyword())
                        .or(QStudy.study.content.contains(studySearchRequest.getKeyword())))
                .fetch();
    }

    private List<Study> searchByTitle(StudySearchRequest studySearchRequest) {
        return jpaQueryFactory.selectFrom(QStudy.study)
                .limit(studySearchRequest.getSize())
                .offset(studySearchRequest.getOffset())
                .orderBy(QStudy.study.id.desc())
                .where(QStudy.study.title.contains(studySearchRequest.getKeyword()))
                .fetch();
    }


}
