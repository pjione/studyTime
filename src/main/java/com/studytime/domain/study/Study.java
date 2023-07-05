package com.studytime.domain.study;

import com.studytime.domain.BaseTimeEntity;
import com.studytime.domain.enums.*;
import com.studytime.domain.user.User;
import com.studytime.web.request.StudyModifyRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Study extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_id")
    private Long id;

    private String title;
    @Lob
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private Integer joinCnt;
    private Integer recruitCnt;
    private LocalDate startedAt;
    private LocalDate expiredAt;


    @Enumerated(value = EnumType.STRING)
    private Period period;
    @Enumerated(value = EnumType.STRING)
    private StudyStatus status;
    @Enumerated(value = EnumType.STRING)
    private ProcessType processType;
    @Enumerated(value = EnumType.STRING)
    private Category category;

    @Embedded
    private Address address;

    @Builder
    public Study(String title, String content, User user, Integer recruitCnt, LocalDate startedAt, LocalDate expiredAt, Period period, ProcessType processType, Category category, Address address) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.joinCnt = 1;
        this.recruitCnt = recruitCnt;
        this.startedAt = startedAt;
        this.expiredAt = expiredAt;
        this.period = period;
        this.status = StudyStatus.READY;
        this.processType = processType;
        this.category = category;
        this.address = address;
    }

    public void addJoinCnt(){
        joinCnt++;
    }
    public void changeStudy(StudyModifyRequest studyModifyRequest){
        this.title = studyModifyRequest.getTitle();
        this.content = studyModifyRequest.getContent();
        this.recruitCnt = studyModifyRequest.getRecruitCnt();
        this.category = studyModifyRequest.getCategory();
        this.address = new Address(studyModifyRequest.getCity()
                ,studyModifyRequest.getStreet()
                ,studyModifyRequest.getZipcode());
        this.startedAt = studyModifyRequest.getStartedAt();
        this.expiredAt = studyModifyRequest.getExpiredAt();
        this.period = studyModifyRequest.getPeriod();
        this.processType = studyModifyRequest.getProcessType();

    }
}
