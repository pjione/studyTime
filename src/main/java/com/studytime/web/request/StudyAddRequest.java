package com.studytime.web.request;

import com.studytime.domain.enums.Period;
import com.studytime.domain.study.Address;
import com.studytime.domain.enums.Category;
import com.studytime.domain.enums.ProcessType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
public class StudyAddRequest {
    @NotBlank(message = "로그인을 해주세요.")
    private final String userAccount;
    @NotBlank(message = "제목을 입력해주세요.")
    private final String title;
    @NotBlank(message = "내용을 입력해주세요.")
    private final String content;
    @NotBlank(message = "카테고리를 입력해주세요.")
    private final Category category;
    private final Address address;
    @NotBlank(message = "인원을 입력해주세요.")
    @Min(value = 2, message = "2명 이상 가능합니다.")
    private final Integer recruitCnt;
    @NotBlank(message = "시작 날짜를 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy_MM_dd")
    private final LocalDate startedAt;
    @DateTimeFormat(pattern = "yyyy_MM_dd")
    private final LocalDate expiredAt;
    @NotBlank(message = "개월을 입력해주세요.")
    private final Period period;
    @NotBlank(message = "진행방식을 입력해주세요.")
    private final ProcessType processType;
    @Builder
    public StudyAddRequest(String userAccount, String title, String content, Category category, Address address, Integer recruitCnt, LocalDate startedAt, LocalDate expiredAt, Period period, ProcessType processType) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.category = category;
        this.address = address;
        this.recruitCnt = recruitCnt;
        this.startedAt = startedAt;
        this.expiredAt = expiredAt;
        this.period = period;
        this.processType = processType;
    }
}