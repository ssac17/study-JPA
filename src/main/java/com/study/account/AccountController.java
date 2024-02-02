package com.study.account;

import com.study.domain.Account;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;


@Controller
@RequiredArgsConstructor
public class AccountController {


    private final SignUpFormValidator signUpFormValidator;
    private final AccountService accountService;
    private final AccountRepository accountRepository;


    //@InitBinder: 웹 요청 파라미터를 자바 객체에 바인딩하는 과정을 커스터마이징하려는 경우
    @InitBinder("signUpForm")//WebDataBinder 로 웹의 파라미터를 받음
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpFormValidator);
    }

    @GetMapping("/sign-up")
    private String signupForm(Model model) {
                                       //카멜케이스로 작성하면 생략 가능, new SignUpForm()만 작성해도 됨
        model.addAttribute("signUpForm", new SignUpForm());
        return "account/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpSubmit(@Valid SignUpForm signUpForm, Errors error) {
        if(error.hasErrors()) {
            return "account/sign-up";
        }
        //메일인증 성공 후 로그인 처리
        Account account = accountService.processNewAccount(signUpForm);
        accountService.login(account);
        return "redirect:/";
     }

    @GetMapping("/check-email-token")
    private String checkEmailToken(String token, String email, Model model) {
        Account account = accountRepository.findByEmail(email);
        String view = "account/checked-email";

        //계정이 없을경우
        if(account == null) {
            model.addAttribute("error", "wrong email");
            return view;
        }

        //token 일치여부 확인
        if(!account.isValidToken(token)) {
            model.addAttribute("error", "wrong token");
            return view;
        }
        account.completeSignUp();         //count(): JPA에 기본 정의된 메서드

        accountService.login(account);
        model.addAttribute("numberOfUser", accountRepository.count());
        model.addAttribute("nickname", account.getNickname());
        model.addAttribute("email", account.getEmail());
        return view;
    }
    @GetMapping("/check-email")
    public String checkEmailPage(@CurrentUser Account account, Model model) {

        String view = "account/check-email";
        model.addAttribute("email", account.getEmail());
        return view;
    }


    @GetMapping("/resend/confirm-email")
    public String resendMail(@CurrentUser Account account, Model model){
        accountService.sendSignUpConfirmEmail(account);
        return "redirect:/";
    }


    
}
