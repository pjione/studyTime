package com.studytime.domain.study.service;

import com.studytime.domain.enums.Category;
import com.studytime.domain.enums.Period;
import com.studytime.domain.enums.ProcessType;
import com.studytime.domain.study.Address;
import com.studytime.domain.study.Study;
import com.studytime.domain.study.repository.StudyRepository;
import com.studytime.domain.user.Gender;
import com.studytime.domain.user.User;
import com.studytime.domain.user.repository.UserRepository;
import com.studytime.web.request.StudyAddRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class StudyServiceImplTest {

    @Autowired
    private StudyService studyService;
    @Autowired
    private StudyRepository studyRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void userSet(){
        studyRepository.deleteAll();

        User user = User.builder()
                .name("지원")
                .gender(Gender.valueOf("MAN"))
                .phone("010-1111-1111")
                .userAccount("asdf1234")
                .password("1234")
                .build();
        userRepository.save(user);
    }

    @Test
    @DisplayName("스터디 등록")
    void addStudy(){

        LocalDate expiredAt = LocalDate.of(2023, 6, 25);

        StudyAddRequest studyAddRequest = StudyAddRequest.builder()
                .userAccount("asdf1234")
                .period(Period.ONE)
                .zipcode("10123")
                .city("서울시")
                .street("테헤란로")
                .expiredAt(expiredAt)
                .content("코딩스터디입니다.")
                .title("안녕하세요.")
                .category(Category.CODING)
                .recruitCnt(3)
                .startedAt(expiredAt.plusDays(2))
                .processType(ProcessType.ON)
                .build();

        //when
        studyService.addStudy(studyAddRequest);

        //then
        Study study = studyRepository.findAll().get(0);

        assertEquals("안녕하세요.", study.getTitle());
    }
}