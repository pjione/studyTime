package com.studytime.domain.study.repository;

import com.studytime.domain.enums.Category;
import com.studytime.domain.enums.Period;
import com.studytime.domain.enums.ProcessType;
import com.studytime.domain.enums.StudyStatus;
import com.studytime.domain.study.Address;
import com.studytime.domain.study.Study;
import com.studytime.domain.user.Gender;
import com.studytime.domain.user.User;
import com.studytime.domain.user.repository.UserRepository;
import com.studytime.exception.StudyNotFound;
import com.studytime.web.request.StudyAddRequest;
import com.studytime.web.request.StudySearchRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StudyQRepositoryImplTest {

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    void setup(){

        studyRepository.deleteAll();
        userRepository.deleteAll();

        User user = User.builder()
                .name("지원")
                .gender(Gender.valueOf("MAN"))
                .phone("010-1111-1111")
                .userAccount("asdf1234")
                .password("1234")
                .build();
        userRepository.save(user);



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

    }

    @Test
    @DisplayName("스터디 전체 조회 관리자용 - 제목 검색")
    void getListByAdmin(){

        StudySearchRequest searchRequest = StudySearchRequest.builder()
                .keyword("안녕")
                .option("title")
                .page(2)
                .size(10)
                .build();

        List<Study> list = studyRepository.getList(searchRequest);

        Assertions.assertEquals(10, list.size());
    }

    @Test
    @DisplayName("스터디 전체 조회 관리자용 - 제목+내용 검색")
    void getListByAdmin2(){

        StudySearchRequest searchRequest = StudySearchRequest.builder()
                .keyword("코딩")
                .option("titleAndContent")
                .page(2)
                .build();

        List<Study> list = studyRepository.getList(searchRequest);

        Assertions.assertEquals(10, list.size());
    }
    
    @Test
    @DisplayName("스터디 전체 조회 관리자용 - page, size 미입력시 디폴트로 들어가는지 확인")
    void getListByAdmin3(){
        StudySearchRequest searchRequest = StudySearchRequest.builder()
                .keyword("안녕하세요.30")
                .option("title")
                .build();

        List<Study> list = studyRepository.getList(searchRequest);

        Assertions.assertEquals(1, list.size());
    }

    @Test
    @DisplayName("스터디 전체 조회 관리자용 - 키워드 입력 x")
    void getListByAdmin4(){
        StudySearchRequest searchRequest = StudySearchRequest.builder()
                .size(5)
                .build();

        List<Study> list = studyRepository.getList(searchRequest);

        Assertions.assertEquals(5, list.size());
        Assertions.assertEquals("안녕하세요.30", list.get(0).getTitle());
    }

    @Test
    @DisplayName("스터디 상태 승인으로 변경 - 관리자")
    void changeStudyStatus(){
        StudySearchRequest searchRequest = StudySearchRequest.builder()
                .build();

        List<Study> list = studyRepository.getList(searchRequest);

        //when
        studyRepository.updateStudyStatus(StudyStatus.PROGRESSED, list.get(0).getId());
        em.clear();

        Study study = studyRepository.findById(list.get(0).getId()).orElseThrow(StudyNotFound::new);

        //then
        assertEquals(StudyStatus.PROGRESSED, study.getStatus());

    }
    
}