package com.studytime.web.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StudySearchRequest {

    private final Integer size;
    private final Integer page;
    private final String keyword;
    private final String option;

    @Builder
    public StudySearchRequest(Integer page, Integer size, String keyword, String option) {
        this.keyword = keyword == null ? "" : keyword;
        this.option = option == null ? "" : option;
        this.size = (size == null) || (size < 1) ? 10 : size;
        this.page = (page == null) || (page < 1) ? 1 : page;
    }

    public Integer getOffset(){
        return (page - 1) * size;
    }
}
