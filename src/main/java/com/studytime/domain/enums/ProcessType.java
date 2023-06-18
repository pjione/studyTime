package com.studytime.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ProcessType {
    UNDEFINED("미정"), ON("온라인"), OFF("오프라인");

    private final String processType;

    @JsonCreator
    public static ProcessType from(String s) {
        return ProcessType.valueOf(s.toUpperCase());
    }
}
