package com.studytime.domain.user.Service;

import com.studytime.domain.user.User;
import com.studytime.domain.user.repository.UserRepository;
import com.studytime.exception.AlreadyExistsUserAccountException;
import com.studytime.web.request.JoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class JoinServiceImpl implements JoinService{
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void join(JoinRequest joinRequest) {

        Optional<User> findUser = userRepository.findByUserAccount(joinRequest.getUserAccount());
        if(findUser.isPresent()){
            throw new AlreadyExistsUserAccountException();
        }
        User user = User.builder()
                .userAccount(joinRequest.getUserAccount())
                .password(joinRequest.getPassword()) //todo 비밀번호 암호화
                .name(joinRequest.getName())
                .gender(joinRequest.getGender())
                .phone(joinRequest.getPhone())
                .build();

        userRepository.save(user);
    }
}
