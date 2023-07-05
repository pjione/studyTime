package com.studytime.exception;

public class AlreadyApprovedUser extends StudyTimeException{

    public AlreadyApprovedUser() {
        super("이미 처리된 회원입니다.");
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
