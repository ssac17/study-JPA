package com.study.account;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;



@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    /*
    * @MockBean
    * Mockito는 자바에서 사용되는 유닛 테스트를 위한 mock 프레임워크,
    * Spring 테스트 컨텍스트 내에서 특정 빈(bean)을 Mockito mock으로 대체하도록 지정하는 역할
    * */
    @MockBean
    private JavaMailSender javaMailSender;

    @Test
    @DisplayName("회원 가입 처리 - 입력값 오류")
    void signUpSubmitTest_withWorngInput() throws Exception{
        mockMvc.perform(post("/sign-up")
                .param("nickname", "sky")
                .param("email", "ghkdkgksmf!.ad")
                .param("password", "1dd"))
                .andExpect(status().isOk())
                .andExpect(view().name("account/sign-up"));

    }

    @Test
    @DisplayName("회원 가입 처리 - 입력값 정상")
    void signUpSubmitTest_withCorrectInput() throws Exception{
        String mail = "ghkdkgksmf13@naver.com";
        mockMvc.perform(post("/sign-up")
                        .param("nickname", "sky")
                        .param("email", mail)
                        .param("password", "1dd3sd3td"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        assertTrue(accountRepository.existsByEmail(mail));
        then(javaMailSender).should().send(any(SimpleMailMessage.class));

    }

}