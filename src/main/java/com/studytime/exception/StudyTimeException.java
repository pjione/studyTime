package com.studytime.exception;

import lombok.Getter;

@Getter
public abstract class StudyTimeException extends RuntimeException{
    public StudyTimeException(String message) {
        super(message);
    }
    public abstract int getStatusCode();
}
