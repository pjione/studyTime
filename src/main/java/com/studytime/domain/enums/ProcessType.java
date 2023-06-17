package com.studytime.domain.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ProcessType {
    UNDEFINED("미정"), ON("온라인"), OFF("오프라인");

    private final String processType;
}
