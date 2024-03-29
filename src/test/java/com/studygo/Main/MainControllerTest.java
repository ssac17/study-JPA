package com.studygo.Main;

import com.studygo.account.AccountRepository;
import com.studygo.account.AccountService;
import com.studygo.account.SignUpForm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MainControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void BeforeEach() {
        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setNickname("sky");
        signUpForm.setEmail("ghkdgksmf13@naver.com");
        signUpForm.setPassword("123123123");
        accountService.processNewAccount(signUpForm);
    }


    @AfterEach
    void AfterEach() {
        accountRepository.deleteAll();
    }


    @Test
    @DisplayName("이메일로 로그인")
    void login_with_email() throws Exception {

        mockMvc.perform(post("/login")
                        .param("username", "ghkdgksmf13@naver.com")
                        .param("password", "123123123")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated().withUsername("sky"));
    }

    @Test
    @DisplayName("닉네임으로 로그인")
    void login_with_nick() throws Exception {

        mockMvc.perform(post("/login")
                        .param("username", "sky")
                        .param("password", "123123123")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated().withUsername("sky"));
    }

    @Test
    @DisplayName("로그인 실패")
    void login_fail() throws Exception {

        mockMvc.perform(post("/login")
                        .param("username", "sky")
                        .param("password", "123")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(unauthenticated());
    }

    @Test
    @DisplayName("로그아웃")
    void logout() throws Exception {

        mockMvc.perform(post("/logout")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(unauthenticated());
    }
}