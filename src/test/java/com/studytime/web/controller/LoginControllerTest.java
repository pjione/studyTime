package com.studytime.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
class LoginControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void set(){
        User user = User.builder()
                .userAccount("jiwon")
                .name("지원")
                .password("1234")
                .build();

        userRepository.save(user);
    }
    @AfterEach
    void clean(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 성공")
    void login() throws Exception {

        Map<String, String> loginUser = new HashMap<>();

        loginUser.put("userAccount", "jiwon");
        loginUser.put("password", "1234");

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginUser))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("잘못된 비밀번호를 입력하여 로그인 실패한다.")
    void loginFailByPassword() throws Exception {

        Map<String, String> loginUser = new HashMap<>();

        loginUser.put("userAccount", "jiwon");
        loginUser.put("password", "12345");

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginUser))
                )
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("아이디 또는 비밀번호가 일치하지 않습니다."))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("비밀번호에 공백을 입력해서 검증을 통과하지 못해 로그인 실패한다.")
    void loginFailByBlankPassword() throws Exception {

        Map<String, String> loginUser = new HashMap<>();

        loginUser.put("userAccount", "jiwon");
        loginUser.put("password", "");

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginUser))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.password").value("비밀번호를 입력해주세요."))

                .andDo(MockMvcResultHandlers.print());

    }
}