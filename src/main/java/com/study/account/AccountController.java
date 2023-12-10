package com.study.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

    @GetMapping("/sign-up")
    public String signupForm(Model model) {
                                       //카멜케이스로 작성하면 생략 가능, new SignUpForm()만 작성해도 됨
        model.addAttribute("signUpForm", new SignUpForm());
        return "account/sign-up";
    }
}
