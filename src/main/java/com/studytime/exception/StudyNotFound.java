package com.studytime.exception;

public class StudyNotFound extends StudyTimeException{
    public StudyNotFound() {
        super("존재하지 않는 스터디입니다.");
    }

    @Override
    int getStatusCode() {
        return 404;
    }
}
