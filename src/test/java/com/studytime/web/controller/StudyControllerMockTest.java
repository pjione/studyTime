package com.studytime.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studytime.domain.study.repository.StudyRepository;
import com.studytime.domain.study.service.StudyService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = StudyController.class)
class StudyControllerMockTest {

    @MockBean
    private StudyService studyService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    @DisplayName("스티디 참여 신청")
    void joinStudy() throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("userAccount", "jiwon1234");

        mockMvc.perform(MockMvcRequestBuilders.post("/study/{studyId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(map))
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("스티디 참여 신청시 UserAccount값이 안넘어가면 빈 밸리데이션 익셉션이 발생한다.")
    void joinStudyFail() throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("userAccount", "");

        mockMvc.perform(MockMvcRequestBuilders.post("/study/{studyId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(map))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.userAccount").value("아이디를 불러올 수 없습니다."))

                .andDo(MockMvcResultHandlers.print());
    }
}