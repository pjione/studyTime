package com.studytime.exception;

public class InvalidLoginInformation extends StudyTimeException{
    public InvalidLoginInformation() {
        super("아이디 또는 비밀번호가 일치하지 않습니다.");
    }
    @Override
    int getStatusCode() {
        return 401;
    }
}
