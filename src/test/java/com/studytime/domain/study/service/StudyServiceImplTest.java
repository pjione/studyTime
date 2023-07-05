package com.studytime.domain.study.service;

import com.studytime.domain.enums.*;
import com.studytime.domain.study.Address;
import com.studytime.domain.study.Study;
import com.studytime.domain.study.repository.StudyRepository;
import com.studytime.domain.study.repository.StudyUserRepository;
import com.studytime.domain.studyuser.StudyUser;
import com.studytime.domain.user.Gender;
import com.studytime.domain.user.User;
import com.studytime.domain.user.repository.UserRepository;
import com.studytime.exception.AlreadyApprovedUser;
import com.studytime.exception.AlreadyExistsStudyUser;
import com.studytime.exception.StudyNotFound;
import com.studytime.exception.UserNotFound;
import com.studytime.web.request.StudyAddRequest;
import com.studytime.web.request.StudyJoinRequest;
import com.studytime.web.request.StudySearchRequest;
import com.studytime.web.response.StudyResponse;
import org.assertj.core.api.Assertions;
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

import static org.assertj.core.api.Assertions.*;
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

        Study study = addStudyMethod();

        //when
        studyService.approveStudy(study.getId());

        //then
        StudyUser studyUser = studyUserRepository.findAll().get(0);

        assertEquals("PROGRESSED", studyUser.getStatus().name());
        assertEquals("LEADER", studyUser.getStudyUserStatus().name());
        assertEquals(study, studyUser.getStudy());
        assertEquals(user, studyUser.getUser());

    }

    @Test
    @DisplayName("스터디 참여 신청시 스터디 유저 테이블에 승인 대기 상태로 정보가 들어간다.")
    void joinStudy(){

        Study study = addStudyMethod();

        approveStudyMethod(study);

        User findUser = newUserSet();

        StudyJoinRequest studyJoinRequest = new StudyJoinRequest(findUser.getUserAccount());

        //when
        studyService.joinStudy(study.getId(), studyJoinRequest);

        //then
        StudyUser studyUser = studyUserRepository.findByStudyIdAndUserId(study.getId(), findUser.getId()).get();

        assertThat(studyUser.getStudy()).isEqualTo(study);
        assertThat(findUser).isEqualTo(studyUser.getUser());
        assertThat(studyUser.getStudyUserStatus()).isEqualTo(StudyUserStatus.GENERAL);
        assertThat(studyUser.getStatus()).isEqualTo(StudyStatus.READY);

    }

    @Test
    @DisplayName("스터디 참여 신청시 중복으로 익셉션이 발생한다.")
    void joinStudyFail(){

        Study study = addStudyMethod();

        approveStudyMethod(study);

        StudyJoinRequest studyJoinRequest = new StudyJoinRequest(user.getUserAccount());


        //expected
        assertThatThrownBy(() -> studyService.joinStudy(study.getId(), studyJoinRequest))
                .isInstanceOf(AlreadyExistsStudyUser.class)
                .hasMessage("해당 스터디에 이미 신청되었습니다.");
    }

    @Test
    @DisplayName("스터디 참여 신청을 승인하여 스터디 상태 컬럼이 PROGRESSED 로 바뀐다.")
    void joinStudyApprove(){

        Study study = addStudyMethod();

        approveStudyMethod(study);

        User findUser = newUserSet();

        StudyJoinRequest studyJoinRequest = new StudyJoinRequest(findUser.getUserAccount());

        joinStudyMethod(study, studyJoinRequest);

        //when
        studyService.approveStudyUser(study.getId(), studyJoinRequest);

        //then
        StudyUser studyUser = studyUserRepository.findByStudyIdAndUserId(study.getId(), findUser.getId()).get();

        assertThat(studyUser.getStatus()).isEqualTo(StudyStatus.PROGRESSED);
    }

    @Test
    @DisplayName("스터디 참여 신청을 중복 승인하여 익셉션이 발생한다.")
    void joinStudyApproveFail(){

        Study study = addStudyMethod();

        approveStudyMethod(study);

        User findUser = newUserSet();

        StudyJoinRequest studyJoinRequest = new StudyJoinRequest(findUser.getUserAccount());

        joinStudyMethod(study, studyJoinRequest);

        studyService.approveStudyUser(study.getId(), studyJoinRequest);

        //expected
        assertThatThrownBy(() -> studyService.approveStudyUser(study.getId(), studyJoinRequest))
                .hasMessage("이미 승인된 회원입니다.")
                .isInstanceOf(AlreadyApprovedUser.class);
    }

    @Test
    @DisplayName("스터디 참여 신청을 거절하여 스터디 상태 컬럼이 REFUSED 로 바뀐다.")
    void joinStudyRefuse(){

        Study study = addStudyMethod();

        approveStudyMethod(study);

        User findUser = newUserSet();

        StudyJoinRequest studyJoinRequest = new StudyJoinRequest(findUser.getUserAccount());

        joinStudyMethod(study, studyJoinRequest);

        //when
        studyService.refuseStudyUser(study.getId(), studyJoinRequest);

        //then
        StudyUser studyUser = studyUserRepository.findByStudyIdAndUserId(study.getId(), findUser.getId()).get();

        assertThat(studyUser.getStatus()).isEqualTo(StudyStatus.REFUSED);
    }


    private void joinStudyMethod(Study study, StudyJoinRequest studyJoinRequest) {
        Study findStudy = studyRepository.findById(study.getId()).orElseThrow(StudyNotFound::new);
        User findUserByAccount = userRepository.findByUserAccount(studyJoinRequest.getUserAccount()).orElseThrow(UserNotFound::new);

        StudyUser studyUser = StudyUser.builder()
                .study(findStudy)
                .user(findUserByAccount)
                .status(StudyStatus.READY)
                .studyUserStatus(StudyUserStatus.GENERAL)
                .build();

        studyUserRepository.save(studyUser);
    }
    

    private User newUserSet() {
        User user = User.builder()
                .name("지원New")
                .gender(Gender.valueOf("MAN"))
                .phone("010-1111-1111")
                .userAccount("newUser")
                .password("1234")
                .build();

        return userRepository.save(user);
    }

    private Study addStudyMethod() {
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

        return studyRepository.save(study);
    }


    private void approveStudyMethod(Study study) {
        studyRepository.updateStudyStatus(StudyStatus.PROGRESSED, study.getId());

        Study findStudy = studyRepository.findById(study.getId()).orElseThrow(StudyNotFound::new);
        User findUser = userRepository.findById(findStudy.getUser().getId()).orElseThrow(UserNotFound::new);

        StudyUser studyUser = StudyUser.builder()
                .study(study)
                .user(user)
                .status(StudyStatus.PROGRESSED)
                .studyUserStatus(StudyUserStatus.LEADER)
                .build();

        studyUserRepository.save(studyUser);
    }
}