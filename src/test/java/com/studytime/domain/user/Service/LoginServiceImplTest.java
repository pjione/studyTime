package com.studytime.domain.user.Service;

import com.studytime.domain.user.Gender;
import com.studytime.domain.user.User;
import com.studytime.domain.user.repository.UserRepository;
import com.studytime.exception.InvalidLoginInformation;
import com.studytime.web.request.LoginRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LoginServiceImplTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoginService loginService;

    @BeforeEach
    void userSet(){

        userRepository.deleteAll();

        User user = User.builder()
                .name("지원")
                .gender(Gender.valueOf("MAN"))
                .phone("010-1111-1111")
                .userAccount("jiwon")
                .password("1234")
                .build();
        userRepository.save(user);
    }
    @Test
    @DisplayName("로그인 성공")
    void loginSuccess(){
        LoginRequest loginRequest = LoginRequest.builder()
                .userAccount("jiwon")
                .password("1234")
                .build();
        String userAccount = loginService.login(loginRequest);
        assertEquals("jiwon", userAccount);
    }

    @Test
    @DisplayName("로그인 실패-비밀번호 불일치")
    void loginFail(){
        LoginRequest loginRequest = LoginRequest.builder()
                .userAccount("jiwon")
                .password("12345")
                .build();

        assertThrows(InvalidLoginInformation.class, () -> loginService.login(loginRequest));
    }


}