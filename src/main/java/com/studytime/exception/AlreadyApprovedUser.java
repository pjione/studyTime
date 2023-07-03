package com.studytime.exception;

public class AlreadyApprovedUser extends StudyTimeException{

    public AlreadyApprovedUser() {
        super("이미 승인된 회원입니다.");
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
