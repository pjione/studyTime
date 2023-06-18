package com.studytime.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studytime.domain.user.Gender;
import com.studytime.domain.user.repository.UserRepository;
import com.studytime.web.request.JoinRequest;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
class JoinControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup(){
        userRepository.deleteAll();
    }
    @AfterEach
    void clean(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void join() throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("userAccount", "jiwon");
        map.put("password", "1234");
        map.put("name", "지원");
        map.put("gender", "man");

        String json = objectMapper.writeValueAsString(map);

        mockMvc.perform(MockMvcRequestBuilders.post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("회원가입 실패 비밀번호 미기입")
    void joinFail() throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("userAccount", "jiwon");
        map.put("name", "지원");
        map.put("gender", "man");

        String json = objectMapper.writeValueAsString(map);

        mockMvc.perform(MockMvcRequestBuilders.post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

    }
}