package com.studytime.domain.study;

import com.studytime.domain.BaseTimeEntity;
import com.studytime.domain.enums.*;
import com.studytime.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private LocalDateTime startedAt;
    private LocalDateTime expiredAt;

    @Enumerated(value = EnumType.STRING)
    private Period period;
    @Enumerated(value = EnumType.STRING)
    private StudyStatus status;
    @Enumerated(value = EnumType.STRING)
    private ProcessType processType;
    @Enumerated(value = EnumType.STRING)
    private Category category;
    @Enumerated(value = EnumType.STRING)
    private RecruitCnt recruitCnt;

    @Embedded
    private Address address;

    @Builder
    public Study(String title, String content, User user, LocalDateTime startedAt, LocalDateTime expiredAt, Period period, ProcessType processType, Category category, RecruitCnt recruitCnt, Address address) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.startedAt = startedAt;
        this.expiredAt = expiredAt;
        this.period = period;
        this.processType = processType;
        this.category = category;
        this.recruitCnt = recruitCnt;
        this.address = address;
    }
    public void addJoinCnt(){
        joinCnt++;
    }
    public void changeStatus(StudyStatus status){
        this.status = status;
    }
}
