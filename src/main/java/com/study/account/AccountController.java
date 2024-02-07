package com.study.account;

import com.study.domain.Account;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;


@Controller
@RequiredArgsConstructor
@Log4j2
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
        if (error.hasErrors()) {
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
        if (account == null) {
            model.addAttribute("error", "wrong email");
            return view;
        }
        //token 일치여부 확인
        if (!account.isValidToken(token)) {
            model.addAttribute("error", "wrong token");
            return view;
        }

        accountService.completeSignUp(account);

                                                        //count(): JPA에 기본 정의된 메서드
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
    public String resendMail(@CurrentUser Account account, Model model) {
        accountService.sendSignUpConfirmEmail(account);
        return "redirect:/";
    }

    @GetMapping("/profile/{nickname}")
    public String viewProfile(@PathVariable("nickname") String nickname, Model model, @CurrentUser Account account) {
        log.info("--- viewProfile ---");
        log.info("nickname = {}", nickname);
        Account byNickname = accountRepository.findByNickname(nickname);
        if(byNickname == null) {
            throw  new IllegalArgumentException(nickname);
        }
        model.addAttribute(byNickname);
        model.addAttribute("isOwner", byNickname.equals(account));

        return "account/profile";
    }

    @GetMapping("/email-login")
    public String emailLoginForm() {
        return "account/email-login";
    }

    @PostMapping("/email-login")
    public String sendEmailLoginLink(String email, Model model, RedirectAttributes attributes) {
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            model.addAttribute("error", "유효한 이메일 주소가 아닙니다.");
            return "account/email-login";
        }

//        if (!account.canSendConfirmEmail()) {
//            model.addAttribute("error", "이메일 로그인은 1시간 뒤에 사용할 수 있습니다.");
//            return "account/email-login";
//        }

        accountService.sendLoginLink(account);
        attributes.addFlashAttribute("message", "이메일 인증 메일을 발송했습니다.");
        return "redirect:/email-login";
    }

    @GetMapping("/login-by-email")
    public String loginByEmail(String token, String email, Model model) {
        Account account = accountRepository.findByEmail(email);
        String view = "account/logged-in-by-email";
        if (account == null || !account.isValidToken(token)) {
            model.addAttribute("error", "로그인할 수 없습니다.");
            return view;
        }

        accountService.login(account);
        return view;
    }

}

