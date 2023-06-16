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

    private UserRepository userRepository;
    @Override
    public String login(LoginRequest loginRequest) {
        User findUser = userRepository.findByUserAccountAndPassword(loginRequest.getUserAccount(), loginRequest.getPassword()).orElseThrow(InvalidLoginInformation::new);
        return findUser.getUserAccount();
    }
}
