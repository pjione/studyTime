package com.studytime.domain.user.Service;

import com.studytime.web.request.LoginRequest;
import org.springframework.stereotype.Service;

public interface LoginService {
    String login(LoginRequest loginRequest);
}
