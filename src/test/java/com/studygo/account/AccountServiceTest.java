package com.studygo.account;

import com.studygo.domain.Account;
import com.studygo.email.EmailMessage;
import com.studygo.email.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class AccountServiceTest {



    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @MockBean
    private EmailService emailService;

    @Test
    @DisplayName("회원 가입 처리 - 입력값 정상")
    void signUpSubmitTest_withCorrectInput() throws Exception{
        String mail = "ghkdkgksmf13@naver.com";
        String password = "1dd3sd3td";
        mockMvc.perform(post("/sign-up")
                        .param("nickname", "sky")
                        .param("templates/email", mail)
                        .param("password", password)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andExpect(authenticated().withUsername("sky"));
        Account account = accountRepository.findByEmail(mail);
        assertNotNull(account);
        assertNotEquals(account.getPassword(), password);
        then(emailService).should().sendEmail(any(EmailMessage.class));
    }
}







































