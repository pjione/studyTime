package com.studytime.web.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class StudyJoinRequest {

    @NotBlank(message = "아이디를 불러올 수 없습니다.")
    private String userAccount;


}
