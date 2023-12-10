package com.study.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    /*
    * MockMvc
    * Spring Framework 제공하는 테스트 유틸리티, 웹 애플리케이션의 Controller를 테스트하는 데 사용
    * 실제 서블릿 컨테이너를 실행하지 않고 HTTP 요청을 디스패처 서블릿에 보내고, 그 결과를 검증할 수 있다
    * */
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("회원가입 화면 보이는지 테스트")
    void signupForm() throws Exception {
        mockMvc.perform(get("/sign-up"))
                //andExpect: 검증
                .andExpect(status().isOk())
                //view: view 이름 검증
                .andExpect(view().name("account/sign-up"));


    }
}