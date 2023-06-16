package com.studytime.exception;

public abstract class StudyTimeException extends RuntimeException{
    public StudyTimeException(String message) {
        super(message);
    }
    abstract int getStatusCode();
}
