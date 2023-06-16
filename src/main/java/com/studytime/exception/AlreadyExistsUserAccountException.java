package com.studytime.exception;

public class AlreadyExistsUserAccountException extends StudyTimeException {
    public AlreadyExistsUserAccountException() {
        super("이미 존재하는 아이디입니다.");
    }
    @Override
    int getStatusCode() {
        return 400;
    }
}
