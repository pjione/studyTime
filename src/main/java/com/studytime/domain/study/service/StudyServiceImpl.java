package com.studytime.domain.study.service;

import com.studytime.domain.enums.StudyStatus;
import com.studytime.domain.enums.StudyUserStatus;
import com.studytime.domain.study.Address;
import com.studytime.domain.study.Study;
import com.studytime.domain.study.repository.StudyRepository;
import com.studytime.domain.study.repository.StudyUserRepository;
import com.studytime.domain.studyuser.StudyUser;
import com.studytime.domain.user.User;
import com.studytime.domain.user.repository.UserRepository;
import com.studytime.exception.*;
import com.studytime.web.request.StudyAddRequest;
import com.studytime.web.request.StudyJoinRequest;
import com.studytime.web.request.StudyModifyRequest;
import com.studytime.web.request.StudySearchRequest;
import com.studytime.web.response.StudyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyServiceImpl implements StudyService{

    private final StudyRepository studyRepository;
    private final UserRepository userRepository;
    private final StudyUserRepository studyUserRepository;
    @Override
    @Transactional
    public void addStudy(StudyAddRequest studyAddRequest) {

        User user = userRepository.findByUserAccount(studyAddRequest.getUserAccount()).orElseThrow(UnAuthorized::new);

        Study study = Study.builder()
                .user(user)
                .title(studyAddRequest.getTitle())
                .content(studyAddRequest.getContent())
                .category(studyAddRequest.getCategory())
                .recruitCnt(studyAddRequest.getRecruitCnt())
                .address(Address.builder()
                        .street(studyAddRequest.getStreet())
                        .city(studyAddRequest.getCity())
                        .zipcode(studyAddRequest.getZipcode())
                        .build())
                .expiredAt(studyAddRequest.getExpiredAt())
                .startedAt(studyAddRequest.getStartedAt())
                .period(studyAddRequest.getPeriod())
                .processType(studyAddRequest.getProcessType())
                .build();

         studyRepository.save(study);

        // todo -> 등록시 getMapping에  enum타입 카테고리, ProcessType 둥 리스트 반환 (to select box)
    }

    @Override
    public List<StudyResponse> studyList(StudySearchRequest studySearchRequest) {

        return studyRepository.getList(studySearchRequest).stream()
                .map(s -> StudyResponse.builder()
                        .study(s)
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public StudyResponse getStudy(Long studyId) {
        Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFound::new);
        return StudyResponse.builder()
                .study(study)
                .build();
    }

    @Override
    @Transactional
    public void approveStudy(Long studyId) {
        studyRepository.updateStudyStatus(StudyStatus.PROGRESSED, studyId);

        Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFound::new);
        User user = userRepository.findById(study.getUser().getId()).orElseThrow(UserNotFound::new);

        StudyUser studyUser = StudyUser.builder()
                .study(study)
                .user(user)
                .status(StudyStatus.PROGRESSED)
                .studyUserStatus(StudyUserStatus.LEADER)
                .build();

        studyUserRepository.save(studyUser);
    }

    @Override
    @Transactional
    public void refuseStudy(Long studyId) {
        studyRepository.updateStudyStatus(StudyStatus.REFUSED, studyId);
    }

    @Override
    @Transactional
    public void joinStudy(Long studyId, StudyJoinRequest studyJoinRequest) {

        Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFound::new);
        User user = userRepository.findByUserAccount(studyJoinRequest.getUserAccount()).orElseThrow(UserNotFound::new);

        if(studyUserRepository.findByStudyIdAndUserId(study.getId(), user.getId()).isPresent()){
            throw new AlreadyExistsStudyUser();
        }

        StudyUser studyUser = StudyUser.builder()
                .study(study)
                .user(user)
                .status(StudyStatus.READY)
                .studyUserStatus(StudyUserStatus.GENERAL)
                .build();

        studyUserRepository.save(studyUser);
    }

    @Override
    @Transactional
    public void approveStudyUser(Long studyId, StudyJoinRequest studyJoinRequest) {

        User user = userRepository.findByUserAccount(studyJoinRequest.getUserAccount()).orElseThrow(UnAuthorized::new);
        Study findStudy = studyRepository.findById(studyId).orElseThrow(StudyNotFound::new);
        StudyUser findStudyUser = studyUserRepository.findByStudyIdAndUserId(studyId, user.getId()).orElseThrow(UserNotFound::new);

        if(findStudyUser.getStatus().equals(StudyStatus.PROGRESSED)){
            throw new AlreadyApprovedUser();
        }

        findStudy.addJoinCnt();
        findStudyUser.changeStatus(StudyStatus.PROGRESSED);

    }

    @Override
    @Transactional
    public void refuseStudyUser(Long studyId, StudyJoinRequest studyJoinRequest) {

        User user = userRepository.findByUserAccount(studyJoinRequest.getUserAccount()).orElseThrow(UnAuthorized::new);
        StudyUser findStudyUser = studyUserRepository.findByStudyIdAndUserId(studyId, user.getId()).orElseThrow(UserNotFound::new);

        if(findStudyUser.getStatus().equals(StudyStatus.REFUSED)){
            throw new AlreadyApprovedUser();
        }

        findStudyUser.changeStatus(StudyStatus.REFUSED);
    }

    @Override
    @Transactional
    public void modifyStudy(Long studyId, StudyModifyRequest studyModifyRequest) {

        Study findStudy = studyRepository.whenModifyFindById(studyId).orElseThrow(StudyNotFound::new);

        if(!findStudy.getUser().getUserAccount().equals(studyModifyRequest.getUserAccount())){
            throw new UnAuthorized();
        }
        findStudy.changeStudy(studyModifyRequest);

    }
}
