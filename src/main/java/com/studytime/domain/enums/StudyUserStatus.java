package com.studytime.domain.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum StudyUserStatus {
    LEADER("그룹장"), GENERAL("그룹원");

    private final String studyUserStatus;
}
