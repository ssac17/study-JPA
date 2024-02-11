package com.studygo.account;

import com.studygo.domain.Account;
import com.studygo.email.EmailMessage;
import com.studygo.email.EmailService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest
@AutoConfigureMockMvc
@Transactional
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
    private EmailService emailService;


    @Test
    @DisplayName("인증 메일 확인-입력값 오류")
    void checkEmailToken_with_wrong_input() throws Exception {
        mockMvc.perform(get("/check-email-token")
                .param("token", "asdfasdfaf")
                .param("templates/email", "ghkdgksmf13@naver.com"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error"))
                .andExpect(view().name("account/checked-email"))
                .andExpect(unauthenticated());
        ;
    }

    @Test
    @DisplayName("인증 메일 확인-입력값 정상")
    void checkEmailToken() throws Exception {

        String email = "ghkdgksmf13@naver.com";
        String password = "12345678";
        String n = "sky";
        Account account = Account.builder()
                .email(email)
                .password(password)
                .nickname(n)
                .build();

        Account newAccount = accountRepository.save(account);
        newAccount.generateEmailCheckToken();

        mockMvc.perform(get("/check-email-token")
                        .param("token", newAccount.getEmailCheckToken())
                        .param("templates/email", newAccount.getEmail()))
                .andExpect(status().isOk())
                .andExpect(model().attributeDoesNotExist("error"))
                .andExpect(model().attributeExists("nickname"))
                .andExpect(model().attributeExists("numberOfUser"))
                .andExpect(view().name("account/checked-email"))
                .andExpect(authenticated().withUsername(n));
    }


    @Test
    @DisplayName("회원 가입 화면 보이는지 테스트")
    void signUpForm() throws Exception {
        mockMvc.perform(get("/sign-up"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("account/sign-up"))
                .andExpect(model().attributeExists("signUpForm"))
                .andExpect(unauthenticated());
    }


    @Test
    @DisplayName("회원 가입 처리 - 입력값 오류")
    void signUpSubmitTest_withWorngInput() throws Exception{
        mockMvc.perform(post("/sign-up")
                        .param("nickname", "sky")
                        .param("templates/email", "ghkdgksmf13@naver.com")
                        .param("password", "1dd")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("account/sign-up"))
                .andExpect(unauthenticated());
    }

    @Test
    @DisplayName("회원 가입 처리 - 입력값 정상")
    void signUpSubmitTest_withCorrectInput() throws Exception{
        String mail = "ghkdkgksmf13@naver.com";
        mockMvc.perform(post("/sign-up")
                        .param("nickname", "sky")
                        .param("templates/email", mail)
                        .param("password", "1dd3sd3td")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andExpect(authenticated().withUsername("sky"));

        Account account = accountRepository.findByEmail(mail);
        assertNotNull(account);
        assertNotEquals(account.getPassword(), "1dd3sd3td");
        assertNotNull(account.getEmailCheckToken());
        then(emailService).should().sendEmail(any(EmailMessage.class));
    }
}