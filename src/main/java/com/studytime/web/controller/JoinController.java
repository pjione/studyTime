package com.studytime.web.controller;

import com.studytime.domain.user.Service.JoinService;
import com.studytime.web.request.JoinRequest;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public void Join(@RequestBody @Validated JoinRequest joinRequest){
        joinService.join(joinRequest);
    }
}
