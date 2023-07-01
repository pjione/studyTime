package com.studytime.exception;

public class AlreadyExistsStudyUser extends StudyTimeException {

    public AlreadyExistsStudyUser() {
        super("해당 스터디에 이미 신청되었습니다.");
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
