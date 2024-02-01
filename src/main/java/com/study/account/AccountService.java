package com.study.account;

import com.study.domain.Account;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Account processNewAccount(SignUpForm signUpForm) {
        Account newAccount = saveNewAccount(signUpForm);
        newAccount.generateEmailCheckToken();
        sendSignUpConfirmEmail(newAccount);
        return newAccount;
    }

    public Account saveNewAccount(@Valid SignUpForm signUpForm) {

        Account account = Account.builder()
                .email(signUpForm.getEmail())
                .nickname(signUpForm.getNickname())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .studyEnrollmentResultByWeb(true)
                .studyCreateByWeb(true)
                .studyUpdateByWeb(true)
                .build();
        return accountRepository.save(account);
    }

    public void sendSignUpConfirmEmail(Account newAccount) {
        //SimpleMailMessage: 스프링 프레임워크의 메일 전송 기능
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(newAccount.getEmail());
        mailMessage.setSubject("스터디");
        mailMessage.setText("/check-email-token?token="+ newAccount.getEmailCheckToken()
                + "&email=" + newAccount.getEmail());
        javaMailSender.send(mailMessage);
    }

    //메일인증 성공 후 로그인 처리
    public void login(Account account) {

        //사용자 인증정보를 담은 객체
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        account.getNickname(),
                        account.getPassword(),      // 권한 부여
                        List.of(new SimpleGrantedAuthority("ROLE USER")));
        //SecurityContextHolder: 현재 스레드의 보안 컨텍스트를 저장하는 데 사용되는 객체
        //getContext()로 SecurityContext에 담음

        SecurityContextHolder.getContext().setAuthentication(token);
    }
}




































