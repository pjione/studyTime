package com.studytime.domain.study.service;

import com.studytime.domain.enums.Category;
import com.studytime.domain.enums.Period;
import com.studytime.domain.enums.ProcessType;
import com.studytime.domain.study.Address;
import com.studytime.domain.study.Study;
import com.studytime.domain.study.repository.StudyRepository;
import com.studytime.domain.study.repository.StudyUserRepository;
import com.studytime.domain.studyuser.StudyUser;
import com.studytime.domain.user.Gender;
import com.studytime.domain.user.User;
import com.studytime.domain.user.repository.UserRepository;
import com.studytime.web.request.StudyAddRequest;
import com.studytime.web.request.StudySearchRequest;
import com.studytime.web.response.StudyResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @Autowired
    private StudyUserRepository studyUserRepository;

    private User user;

    @BeforeEach
    void userSet(){
        studyRepository.deleteAll();
        userRepository.deleteAll();

        user = User.builder()
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

    @Test
    @DisplayName("스터디 목록")
    void getList(){

        LocalDate expiredAt = LocalDate.of(2023, 6, 25);

        List<Study> studyList = IntStream.range(0, 30)
                .mapToObj(i -> Study.builder()
                        .user(user)
                        .period(Period.ONE)
                        .address(Address.builder()
                                .zipcode("10123")
                                .city("서울시")
                                .street("테헤란로")
                                .build())
                        .expiredAt(expiredAt)
                        .content("코딩스터디입니다.")
                        .title("안녕하세요." + (i+1))
                        .category(Category.CODING)
                        .recruitCnt(3)
                        .startedAt(expiredAt.plusDays(2))
                        .processType(ProcessType.ON)
                        .build()).collect(Collectors.toList());

        studyRepository.saveAll(studyList);

        StudySearchRequest searchRequest = StudySearchRequest.builder()
                .build();
        //when
        List<StudyResponse> studyResponses = studyService.studyList(searchRequest);

        //then
        assertEquals(10L, studyResponses.size());
        assertEquals("안녕하세요.30", studyResponses.get(0).getTitle());
    }

    @Test
    @DisplayName("스터디 조회")
    void getStudy(){

        LocalDate expiredAt = LocalDate.of(2023, 6, 25);

        List<Study> studyList = IntStream.range(0, 30)
                .mapToObj(i -> Study.builder()
                        .user(user)
                        .period(Period.ONE)
                        .address(Address.builder()
                                .zipcode("10123")
                                .city("서울시")
                                .street("테헤란로")
                                .build())
                        .expiredAt(expiredAt)
                        .content("코딩스터디입니다.")
                        .title("안녕하세요." + (i+1))
                        .category(Category.CODING)
                        .recruitCnt(3)
                        .startedAt(expiredAt.plusDays(2))
                        .processType(ProcessType.ON)
                        .build()).collect(Collectors.toList());

        studyRepository.saveAll(studyList);


        //when
        StudyResponse studyResponse = studyService.getStudy(studyList.get(0).getId());

        //then
        assertEquals("안녕하세요.1", studyResponse.getTitle());

    }

    @Test
    @DisplayName("스터디 등록 승인 - 관리자")
    void approveStudy(){

        LocalDate expiredAt = LocalDate.of(2023, 6, 25);

        Study study = Study.builder()
                .user(user)
                .period(Period.ONE)
                .address(Address.builder()
                        .zipcode("10123")
                        .city("서울시")
                        .street("테헤란로")
                        .build())
                .expiredAt(expiredAt)
                .content("코딩스터디입니다.")
                .title("안녕하세요.")
                .category(Category.CODING)
                .recruitCnt(3)
                .startedAt(expiredAt.plusDays(2))
                .processType(ProcessType.ON)
                .build();

        studyRepository.save(study);

        //when
        studyService.approveStudy(study.getId());

        //then
        StudyUser studyUser = studyUserRepository.findAll().get(0);

        assertEquals("PROGRESSED", studyUser.getStatus().name());
        assertEquals("LEADER", studyUser.getStudyUserStatus().name());
        assertEquals(study, studyUser.getStudy());
        assertEquals(user, studyUser.getUser());

    }

}