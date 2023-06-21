package com.studytime.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum StudyStatus {
    READY("대기"), PROGRESSED("숭인"), REFUSED("거절");

    private final String studyStatus;
    @JsonCreator
    public static StudyStatus from(String s) {
        return StudyStatus.valueOf(s.toUpperCase());
    }

}
