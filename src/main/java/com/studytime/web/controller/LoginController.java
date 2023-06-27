package com.studytime.web.controller;

import com.studytime.domain.user.Service.LoginService;
import com.studytime.web.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public String login(@RequestBody @Validated LoginRequest loginRequest, HttpServletRequest httpServletRequest){

        String loginId = loginService.login(loginRequest);

        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("sid", loginId);

        return loginId;
    }
}
