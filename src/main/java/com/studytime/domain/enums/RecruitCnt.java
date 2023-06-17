package com.studytime.domain.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum RecruitCnt {
    TWO("2명", 2), THREE("3명", 3),
    FOUR("4명", 4), FIVE("5명", 5), SIX("6명", 6);
    private final String recruitCntName;
    private final int recruitCntCode;
}
