package com.studytime.domain.study;

import com.studytime.domain.BaseTimeEntity;
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
    @Enumerated(value = EnumType.STRING)
    private Category category;
    @Embedded
    private Address address;

    private Integer recruitCnt;
    private Integer joinCnt;

    private LocalDateTime startedAt;
    private LocalDateTime expiredAt;
    private Integer period;
    @Enumerated(value = EnumType.STRING)
    private StudyStatus status;
    @Enumerated(value = EnumType.STRING)
    private ProcessType processType;

    @Builder
    public Study(String title, String content, User user, Category category, Address address, Integer recruitCnt, Integer joinCnt, LocalDateTime startedAt, LocalDateTime expiredAt, Integer period, StudyStatus status, ProcessType processType) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.category = category;
        this.address = address;
        this.recruitCnt = recruitCnt;
        this.joinCnt = joinCnt;
        this.startedAt = startedAt;
        this.expiredAt = expiredAt;
        this.period = period;
        this.status = status;
        this.processType = processType;
    }
}
