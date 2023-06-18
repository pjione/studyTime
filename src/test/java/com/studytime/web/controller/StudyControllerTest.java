package com.studytime.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studytime.domain.enums.Category;
import com.studytime.domain.enums.Period;
import com.studytime.domain.enums.ProcessType;
import com.studytime.domain.study.repository.StudyRepository;
import com.studytime.domain.user.Gender;
import com.studytime.domain.user.User;
import com.studytime.domain.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

@AutoConfigureMockMvc
@SpringBootTest
public class StudyControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private StudyRepository studyRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;
    @AfterEach
    void clean(){
        studyRepository.deleteAll();
        userRepository.deleteAll();
    }
    @BeforeEach
    void beforeSet(){
        studyRepository.deleteAll();
        userRepository.deleteAll();

        User user = User.builder()
                .name("지원")
                .gender(Gender.MAN)
                .phone("010-1111-1111")
                .userAccount("asdf1234")
                .password("1234")
                .build();
        userRepository.save(user);
    }

    @Test
    @DisplayName("스터디 등록")
    void addStudy() throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("title", "제목입니다.");
        map.put("content", "내용입니다.");
        map.put("userAccount", "asdf1234");
        map.put("city", "서울시");
        map.put("period", "one");
        map.put("expiredAt", "2023-06-25");
        map.put("category", "coding");
        map.put("recruitCnt", 3);
        map.put("processType", "ON");
        map.put("startedAt", "2023-06-27");

        String json = objectMapper.writeValueAsString(map);

        mockMvc.perform(MockMvcRequestBuilders.post("/study")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}
