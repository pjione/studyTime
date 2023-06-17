package com.studytime.domain.user.Service;

import com.studytime.domain.user.Gender;
import com.studytime.domain.user.User;
import com.studytime.domain.user.repository.UserRepository;
import com.studytime.exception.AlreadyExistsUserAccountException;
import com.studytime.web.request.JoinRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JoinServiceImplTest {
    @Autowired
    private JoinService joinService;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원가입 성공")
    void joinSuccess(){
        JoinRequest joinRequest = JoinRequest.builder()
                .name("지원")
                .gender(Gender.MAN)
                .phone("010-1111-1111")
                .userAccount("jiwon")
                .password("1234")
                .build();
        joinService.join(joinRequest);

        List<User> userList = userRepository.findAll();

        assertEquals("jiwon", userList.get(0).getUserAccount());
        assertEquals("jiwon", userRepository.findByUserAccount("jiwon").get().getUserAccount());
        assertEquals(1L, userRepository.count() );
    }

    @Test
    @DisplayName("회원가입 실패")
    void joinFail(){

        User user = User.builder()
                .name("지원")
                .gender(Gender.MAN)
                .phone("010-1111-1111")
                .userAccount("jiwon")
                .password("1234")
                .build();
        userRepository.save(user);

        JoinRequest joinRequest = JoinRequest.builder()
                .name("지원")
                .gender(Gender.MAN)
                .phone("010-1111-1111")
                .userAccount("jiwon")
                .password("1234")
                .build();

        assertThrows(AlreadyExistsUserAccountException.class, () -> joinService.join(joinRequest));
    }
}