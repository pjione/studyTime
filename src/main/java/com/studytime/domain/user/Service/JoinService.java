package com.studytime.domain.user.Service;

import com.studytime.web.request.JoinRequest;
import org.springframework.stereotype.Service;

@Service
public interface JoinService {
    void join(JoinRequest joinRequest);
}
