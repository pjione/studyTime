package com.studytime.web.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class LoginRequest {
    @NotBlank(message = "아이디를 입력해주세요.")
    private final String userAccount;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private final String password;

    @Builder
    public LoginRequest(String userAccount, String password) {
        this.userAccount = userAccount;
        this.password = password;
    }
}
