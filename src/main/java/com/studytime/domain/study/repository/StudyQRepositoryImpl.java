package com.studytime.domain.study.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.studytime.domain.enums.StudyStatus;
import com.studytime.domain.study.QStudy;
import com.studytime.domain.study.Study;
import com.studytime.web.request.StudySearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public class StudyQRepositoryImpl implements StudyQRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Study> getList(StudySearchRequest studySearchRequest) {

        return jpaQueryFactory
                .selectFrom(QStudy.study)
                .limit(studySearchRequest.getSize())
                .offset(studySearchRequest.getOffset())
                .orderBy(QStudy.study.id.desc())
                .where(searchByTitle(studySearchRequest),
                        searchByContent(studySearchRequest),
                        searchByAll(studySearchRequest))
                .fetch();

    }

    private BooleanExpression searchByAll(StudySearchRequest studySearchRequest) {
        return studySearchRequest.getOption().equals("all") ?
                QStudy.study.title.contains(studySearchRequest.getKeyword())
                        .or(QStudy.study.content.contains(studySearchRequest.getKeyword())) : null;
    }

    private BooleanExpression searchByContent(StudySearchRequest studySearchRequest) {
        return studySearchRequest.getOption().equals("content") ?
                QStudy.study.content.contains(studySearchRequest.getKeyword()) : null;
    }

    private BooleanExpression searchByTitle(StudySearchRequest studySearchRequest) {
        return studySearchRequest.getOption().equals("title") ?
                QStudy.study.title.contains(studySearchRequest.getKeyword()) : null;
    }


    @Override
    @Transactional
    public void updateStudyStatus(StudyStatus status, Long studyId) {
        jpaQueryFactory
                .update(QStudy.study)
                .set(QStudy.study.status, status)
                .where(QStudy.study.id.eq(studyId))
                .execute();
    }
}
