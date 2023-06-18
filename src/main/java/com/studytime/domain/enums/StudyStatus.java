package com.studytime.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


public enum StudyStatus {
    READY, PROGRESSED, REFUSED;

    @JsonCreator
    public static StudyStatus from(String s) {
        return StudyStatus.valueOf(s.toUpperCase());
    }

}
