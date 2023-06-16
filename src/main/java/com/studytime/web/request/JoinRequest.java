package com.studytime.web.request;

import com.studytime.domain.user.Gender;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class JoinRequest {
    @NotBlank(message = "아이디를 입력해주세요.")
    private final String userAccount;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private final String password;
    @NotBlank(message = "이름을 입력해주세요.")
    private final String name;
    private final Gender gender;
    private final String phone;
    @Builder
    public JoinRequest(String userAccount, String password, String name, Gender gender, String phone) {
        this.userAccount = userAccount;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
    }
}
