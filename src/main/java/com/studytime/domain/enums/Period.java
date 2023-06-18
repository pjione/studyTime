package com.studytime.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Period {
    UNDEFINED("미정"),
    ONE("1개월"), TWO("2개월"), THREE("3개월"),
    FOUR("4개월"), FIVE("5개월"), SIX("6개월");

    private final String period;

    @JsonCreator
    public static Period from(String s) {
        return Period.valueOf(s.toUpperCase());
    }

}
