package com.studytime.domain.study.service;

import com.studytime.domain.enums.StudyStatus;
import com.studytime.domain.study.Address;
import com.studytime.domain.study.Study;
import com.studytime.domain.study.repository.StudyRepository;
import com.studytime.domain.study.repository.StudyUserRepository;
import com.studytime.domain.studyuser.StudyUser;
import com.studytime.domain.user.User;
import com.studytime.domain.user.repository.UserRepository;
import com.studytime.exception.StudyNotFound;
import com.studytime.exception.UnAuthorized;
import com.studytime.exception.UserNotFound;
import com.studytime.web.request.StudyAddRequest;
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
                .build();

        studyUserRepository.save(studyUser);
    }

    @Override
    public void refuseStudy(Long studyId) {
        studyRepository.updateStudyStatus(StudyStatus.REFUSED, studyId);
    }
}
