package com.studytime.domain.studyuser;

import com.studytime.domain.enums.StudyUserStatus;
import com.studytime.domain.study.Study;
import com.studytime.domain.enums.StudyStatus;
import com.studytime.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_user_id")
    private Long id;
    private StudyStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;
    private StudyUserStatus studyUserStatus;
    @Builder
    public StudyUser(StudyStatus status, User user, Study study, StudyUserStatus studyUserStatus) {
        this.status = status;
        this.user = user;
        this.study = study;
        this.studyUserStatus = studyUserStatus;
    }
}
