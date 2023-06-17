package com.studytime.domain.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public enum Category {
    CERTIFICATION("자격증"), CODING("코딩"), PROJECT("프로젝트"), ETC("기타");
    private final String category;
}
