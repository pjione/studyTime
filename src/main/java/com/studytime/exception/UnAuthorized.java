package com.studytime.exception;

public class UnAuthorized extends StudyTimeException{
    public UnAuthorized() {
        super("로그인을 해주세요.");
    }
    @Override
    public int getStatusCode() {
        return 401;
    }
}
