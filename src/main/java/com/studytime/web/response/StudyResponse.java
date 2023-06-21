package com.studytime.web.response;

import com.studytime.domain.enums.Category;
import com.studytime.domain.enums.Period;
import com.studytime.domain.enums.ProcessType;
import com.studytime.domain.enums.StudyStatus;
import com.studytime.domain.study.Address;
import com.studytime.domain.study.Study;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class StudyResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final String userAccount;
    private final Integer joinCnt;
    private final Integer recruitCnt;
    private final LocalDate startedAt;
    private final LocalDate expiredAt;
    private final String period;
    private final String status;
    private final String processType;
    private final String category;
    private final Address address;

    @Builder
    public StudyResponse(Study study) {
        this.id = study.getId();
        this.title = study.getTitle();
        this.content = study.getContent();
        this.userAccount = study.getUser().getUserAccount();
        this.joinCnt = study.getJoinCnt();
        this.recruitCnt = study.getRecruitCnt();
        this.startedAt = study.getStartedAt();
        this.expiredAt = study.getExpiredAt();
        this.period = study.getPeriod().getPeriod();
        this.status = study.getStatus().getStudyStatus();
        this.processType = study.getProcessType().getProcessType();
        this.category = study.getCategory().getCategory();
        this.address = study.getAddress();
    }
}
