package com.studytime.web.controller;

import com.studytime.domain.study.service.StudyService;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = AdminController.class)
class AdminControllerTest {

    @MockBean
    private StudyService studyService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("관리자가 스터디 등록 신청을 승인한다.")
    void approveAddStudy() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/study/approve/{studyId}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}