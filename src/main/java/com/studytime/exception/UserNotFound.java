package com.studytime.exception;

public class UserNotFound extends StudyTimeException{
    public UserNotFound() {
        super("존재하지 않는 회원입니다.");
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
