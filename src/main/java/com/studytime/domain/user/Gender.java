package com.studytime.domain.user;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Gender {
    MAN, WOMAN;

    @JsonCreator
    public static Gender from(String s) {
        return Gender.valueOf(s.toUpperCase());
    }
}
