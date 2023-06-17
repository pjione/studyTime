package com.studytime.domain.user.Service;

import com.studytime.domain.user.User;
import com.studytime.domain.user.repository.UserRepository;
import com.studytime.exception.InvalidLoginInformation;
import com.studytime.web.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;
    @Override // todo 로그인 인가 인터셉터 or 리졸버
    public String login(LoginRequest loginRequest) {
        User findUser = userRepository.findByUserAccountAndPassword(loginRequest.getUserAccount(), loginRequest.getPassword()).orElseThrow(InvalidLoginInformation::new);
        return findUser.getUserAccount();
    }

    //todo 회원 목록(페이징) 조회 수정 삭제
}
